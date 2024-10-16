package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.service.TransactionService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaAccountConsumer {
    private final AccountService accountService;
    private final TransactionService transactionService;
    @KafkaListener(topics = "t1_demo_accounts", groupId = "account_group")
    public void listenAccounts(@Payload List<Account> accounts,
                               Acknowledgment ack,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.debug("Account consumer: Обработка новых сообщений");
        try {
            for (Account account : accounts) {
                account.setClientId(Long.valueOf(key));
                accountService.saveAccount(account);
                System.out.println("Saved account: " + account);
            }
        }finally {
            ack.acknowledge();
        }
    }

}
