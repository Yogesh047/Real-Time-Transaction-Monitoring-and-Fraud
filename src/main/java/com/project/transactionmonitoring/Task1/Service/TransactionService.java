package com.project.transactionmonitoring.Task1.Service;

import com.project.transactionmonitoring.Task1.Entity.Transaction;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private static final double AMOUNT_THRESHOLD = 100000;
    private static final int TIME_WINDOW_MINUTES = 5;
    private static final int MAX_TRANSACTIONS_IN_WINDOW = 3;
    private static final List<String> BLACKLISTED_ACCOUNTS = Arrays.asList("1234567890", "9876543210");
    private final List<Map<String, String>> flagged_Transactions = new ArrayList<>();
    public Map<String, String> evaluateTransactions(List<Transaction> transactions) {
        List<String> flaggedReasons = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > AMOUNT_THRESHOLD) {
                flaggedReasons.add("Transaction amount exceeds the limit of ₹1,00,000.");
            }

            if (BLACKLISTED_ACCOUNTS.contains(transaction.getAccountNumber())) {
                flaggedReasons.add("Transaction initiated from a blacklisted account.");
            }

            if (!"India".equalsIgnoreCase(transaction.getLocation().getCountry())) {
                flaggedReasons.add("Transaction initiated from IP outside India.");
            }

            long countRecentTransactions = transactions.stream()
                    .filter(t -> t.getAccountNumber().equals(transaction.getAccountNumber()))
                    .filter(t -> Duration.between(t.getTransactionTime(), transaction.getTransactionTime()).toMinutes() <= TIME_WINDOW_MINUTES)
                    .count();
            if (countRecentTransactions > MAX_TRANSACTIONS_IN_WINDOW) {
                flaggedReasons.add("More than 3 transactions within 5 minutes from the same account.");
            }
            if (!flaggedReasons.isEmpty()) {
                flagged_Transactions.add(Map.of(
                        "transactionId", transaction.getTransactionId(),
                        "accountNumber", transaction.getAccountNumber(),
                        "reason", String.join("; ", flaggedReasons)
                ));
            }
        }

        if (!flaggedReasons.isEmpty()) {
            return Map.of("status", "Fraud", "reason", String.join("; ", flaggedReasons));
        }
        return Map.of("status", "Success", "message", "Transaction is valid.");
    }
    public List<Map<String, String>> getFlaggedTransactions() {
        return new ArrayList<>(flagged_Transactions);
    }
}
