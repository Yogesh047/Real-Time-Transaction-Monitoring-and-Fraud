package com.project.transactionmonitoring.Task2.Controller;

import com.project.transactionmonitoring.Task2.Entity.Loan;
import com.project.transactionmonitoring.Task2.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/eligibility")
    public Map<String, Object> calculateLoanEligibility(@RequestBody List<Loan> loan) {
        return loanService.evaluateEligibility(loan);
    }
    @GetMapping("admin/loan-statistics")
    public Map<String, Object> getLoanStatistics(@RequestHeader("Authorization") String authorization) {
        // Placeholder for authorization validation logic
        if (!"admin-token".equals(authorization)) {
            throw new SecurityException("Unauthorized access");
        }
        return loanService.getLoanStatistics();
    }
}

