package com.project.transactionmonitoring.Task2.Entity;

public class LoanRecord {
    private final String userId;
    private final boolean approved;
    private final String rejectionReason;
    private final double approvedAmount;

    public LoanRecord(String userId, boolean approved, String rejectionReason, double approvedAmount) {
        this.userId = userId;
        this.approved = approved;
        this.rejectionReason = rejectionReason;
        this.approvedAmount = approvedAmount;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }
}
