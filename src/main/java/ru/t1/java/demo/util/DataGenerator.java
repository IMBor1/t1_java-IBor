package ru.t1.java.demo.util;

import ru.t1.java.demo.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static final Random random = new Random();

    public static List<Account> generateAccounts(int count) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Account account = new Account();
            account.setClientId((long) (random.nextInt(1000) + 1));
            account.setAccountType(AccountType.values()[random.nextInt(AccountType.values().length)]);
            account.setBalance(random.nextDouble() * 1000000);
            accounts.add(account);
        }
        return accounts;
    }
    public static List<Transaction> generateTransactions(List<Account> accounts, int count) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(accounts.get(random.nextInt(accounts.size())).getClientId());
            transaction.setAmount(BigDecimal.valueOf(random.nextDouble() * 1000000).setScale(2, BigDecimal.ROUND_HALF_UP));
            transaction.setClientId(accounts.get(random.nextInt(accounts.size())).getClientId());
            transactions.add(transaction);
        }
        return transactions;
    }
    public static List<DataSourceErrorLog> generateErrorLogs(int count) {
        List<DataSourceErrorLog> errorLogs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataSourceErrorLog log = new DataSourceErrorLog();
            log.setStackTrace("Sample stack trace for error " + (i + 1));
            log.setMessage("Sample error message " + (i + 1));
            log.setMethodSignature("com.example.Method" + (i + 1) + "()");
            errorLogs.add(log);
        }
        return errorLogs;
    }
    public static List<TimeLimitExceedLog> generateTimeLimitLogs(int count) {
        List<TimeLimitExceedLog> timeLimitLogs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            TimeLimitExceedLog log = new TimeLimitExceedLog();
            log.setMethodSignature("com.example.Method" + (i + 1) + "()");
            log.setExecutionTime(random.nextInt(2000));
            timeLimitLogs.add(log);
        }
        return timeLimitLogs;
    }
}
