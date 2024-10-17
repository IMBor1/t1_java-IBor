package ru.t1.java.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.*;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;
import ru.t1.java.demo.repository.TransactionRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private DataGenerator dataGenerator;
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
        List<Account> accounts = dataGenerator.generateAccounts(10);
        List<Transaction> transactions = dataGenerator.generateTransactions(accounts, 20);
        List<DataSourceErrorLog> errorLogs = dataGenerator.generateErrorLogs(5);
        List<TimeLimitExceedLog> timeLimitLogs = dataGenerator.generateTimeLimitLogs(5);
        List<Client> clients = dataGenerator.generateClients(10);
        List<User> users = dataGenerator.generateUsers(10);
    }
}
