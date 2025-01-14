package com.project.transactionmonitoring.Task1.Controller;

import com.project.transactionmonitoring.Task1.Entity.Transaction;
import com.project.transactionmonitoring.Task1.Service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/process")
    public Map<String, String> processTransaction(@RequestBody List<Transaction> transactions) {
        return transactionService.evaluateTransactions(transactions);
    }

    @GetMapping("/admin/flagged-transactions")
    public List<Map<String, String>> getFlaggedTransactions(@RequestHeader("Authorization") String authorization) {
        // Placeholder for authorization validation logic
        if (!"admin-token".equals(authorization)) {
            throw new SecurityException("Unauthorized access");
        }
        return transactionService.getFlaggedTransactions();
    }
}

