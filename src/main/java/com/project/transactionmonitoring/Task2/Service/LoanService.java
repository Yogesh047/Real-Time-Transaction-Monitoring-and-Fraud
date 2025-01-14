package com.project.transactionmonitoring.Task2.Service;

import com.project.transactionmonitoring.Task2.Entity.Loan;
import com.project.transactionmonitoring.Task2.Entity.LoanRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoanService {
    private static final double MIN_MONTHLY_INCOME = 30000;
    private static final double MAX_OBLIGATION_RATIO = 0.4;
    private static final int MIN_CREDIT_SCORE = 700;
    private static final int MAX_LOAN_MULTIPLIER = 10;
    private final List<Double> approvedLoanAmounts = new ArrayList<>();
    private final List<String> rejectionReasons = new ArrayList<>();

    public Map<String, Object> evaluateEligibility(List<Loan> loan) {
        Map<String, Object> response = new HashMap<>();
        for (Loan loans : loan) {
            if (loans.getMonthlyIncome() < MIN_MONTHLY_INCOME) {
                rejectionReasons.add("Monthly income is below the required minimum.");
                return Map.of("eligible", false, "reason", "Monthly income is below the required minimum.");

            }

            double obligationRatio = loans.getExistingLoanObligations() / loans.getMonthlyIncome();
            if (obligationRatio > MAX_OBLIGATION_RATIO) {
                rejectionReasons.add("Existing loan obligations exceed 40% of monthly income.");
                return Map.of("eligible", false, "reason", "Existing loan obligations exceed 40% of monthly income.");

            }

            if (loans.getCreditScore() < MIN_CREDIT_SCORE) {
                rejectionReasons.add("Credit score is below the required minimum.");
                return Map.of("eligible", false, "reason", "Credit score is below the required minimum.");
            }

            double maxLoanAmount = loans.getMonthlyIncome() * MAX_LOAN_MULTIPLIER;
            if (loans.getRequestedLoanAmount() > maxLoanAmount) {
                rejectionReasons.add("Requested loan amount exceeds 10x the monthly income.");
                return Map.of("eligible", false, "reason", "Requested loan amount exceeds 10x the monthly income.");
            }

            approvedLoanAmounts.add(loans.getRequestedLoanAmount());

            Map<String, Double> emiBreakdown = Map.of(
                    "8%", calculateEMI(loans.getRequestedLoanAmount(), 8, 12),
                    "10%", calculateEMI(loans.getRequestedLoanAmount(), 10, 12),
                    "12%", calculateEMI(loans.getRequestedLoanAmount(), 12, 12)
            );

            response = Map.of(
                    "eligible", true,
                    "approvedLoanAmount", loans.getRequestedLoanAmount(),
                    "emiBreakdown", emiBreakdown
            );
        }
        return response;
    }
    public Map<String, Object> getLoanStatistics() {
        double averageLoanAmount = approvedLoanAmounts.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        return Map.of(
                "averageLoanApprovalAmount", averageLoanAmount,
                "rejectionReasons", rejectionReasons
        );
    }


    private double calculateEMI(double principal, double rate, int tenure) {
        double monthlyRate = rate / (12 * 100);
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenure)) /
                (Math.pow(1 + monthlyRate, tenure) - 1);
    }

}
