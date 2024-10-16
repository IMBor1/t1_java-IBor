package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.service.TransactionService;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionalConsumer {
    private final TransactionService transactionService;
    @KafkaListener(topics = "t1_demo_transactions", groupId = "transaction_group")
    public void listenTransactions(@Payload List<Transaction> transactions,
                                   Acknowledgment ack,
                                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                   @Header (KafkaHeaders.RECEIVED_KEY) String key ) {
        try {
            for (Transaction transaction : transactions) {
                transaction.setAccountId(Long.valueOf(key));
                transactionService.saveTransaction(transaction);
                System.out.println("Saved transaction: " + transaction);
            }
        } finally {
            ack.acknowledge();
        }
    }
}
