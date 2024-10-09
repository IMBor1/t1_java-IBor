package ru.t1.java.demo.util;

import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.*;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;
import ru.t1.java.demo.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
@Component
public class DataGenerator {
    private static final Random random = new Random();
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final DataSourceErrorLogRepository dataSourceErrorLogRepository;
    private final TimeLimitExceedLogRepository timeLimitExceedLogRepository;

    public DataGenerator(AccountRepository accountRepository, TransactionRepository transactionRepository, DataSourceErrorLogRepository dataSourceErrorLogRepository, TimeLimitExceedLogRepository timeLimitExceedLogRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.dataSourceErrorLogRepository = dataSourceErrorLogRepository;
        this.timeLimitExceedLogRepository = timeLimitExceedLogRepository;
    }
    public List<Account> generateAccounts(int count) {

        for (int i = 0; i < count; i++) {
            Account account = new Account();
            account.setClientId((long) (random.nextInt(1000) + 1));
            account.setAccountType(AccountType.values()[random.nextInt(AccountType.values().length)]);
            account.setBalance(random.nextDouble() * 1000000);
            accountRepository.save(account);
        }
        return accountRepository.findAll();
    }
    public List<Transaction> generateTransactions(List<Account> accounts, int count) {
        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(accounts.get(random.nextInt(accounts.size())).getClientId());
            transaction.setAmount(BigDecimal.valueOf(random.nextDouble() * 1000000).setScale(2, BigDecimal.ROUND_HALF_UP));
            transaction.setClientId(accounts.get(random.nextInt(accounts.size())).getClientId());
            transactionRepository.save(transaction);
        }
            return transactionRepository.findAll();
    }
    public List<DataSourceErrorLog> generateErrorLogs(int count) {
        for (int i = 0; i < count; i++) {
            DataSourceErrorLog log = new DataSourceErrorLog();
            log.setStackTrace("Sample stack trace for error " + (i + 1));
            log.setMessage("Sample error message " + (i + 1));
            log.setMethodSignature("com.example.Method" + (i + 1) + "()");
            dataSourceErrorLogRepository.save(log);
        }
        return dataSourceErrorLogRepository.findAll();
    }
    public
    List<TimeLimitExceedLog> generateTimeLimitLogs(int count) {
        for (int i = 0; i < count; i++) {
            TimeLimitExceedLog log = new TimeLimitExceedLog();
            log.setMethodSignature("com.example.Method" + (i + 1) + "()");
            log.setExecutionTime(random.nextInt(2000));
            timeLimitExceedLogRepository.save(log);
        }
        return timeLimitExceedLogRepository.findAll();
    }
}
