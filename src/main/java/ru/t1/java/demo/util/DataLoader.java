package ru.t1.java.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;
import ru.t1.java.demo.repository.TransactionRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DataSourceErrorLogRepository errorLogRepository;

    @Autowired
    private TimeLimitExceedLogRepository timeLimitLogRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Account> accounts = DataGenerator.generateAccounts(10);
        accountRepository.saveAll(accounts);

        List<Transaction> transactions = DataGenerator.generateTransactions(accounts, 20);
        transactionRepository.saveAll(transactions);

        List<DataSourceErrorLog> errorLogs = DataGenerator.generateErrorLogs(5);
        errorLogRepository.saveAll(errorLogs);

        List<TimeLimitExceedLog> timeLimitLogs = DataGenerator.generateTimeLimitLogs(5);
        timeLimitLogRepository.saveAll(timeLimitLogs);

        System.out.println("Данные успешно сохранены в базу данных!");
    }
}
