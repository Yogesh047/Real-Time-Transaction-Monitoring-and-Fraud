package com.project.transactionmonitoring.Task1.Entity;

import java.time.LocalDateTime;

public class Transaction {

        private String transactionId;
        private String accountNumber;
        private double amount;
        private LocalDateTime transactionTime;
        private String ipAddress;
        private Location location;
        private String transactionType;
        private String remarks;

    public Transaction(String transactionId, String accountNumber, double amount, LocalDateTime transactionTime, String ipAddress, Location location, String transactionType, String remarks) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transactionTime = transactionTime;
        this.ipAddress = ipAddress;
        this.location = location;
        this.transactionType = transactionType;
        this.remarks = remarks;
    }

    // Getters and Setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public LocalDateTime getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(LocalDateTime transactionTime) {
            this.transactionTime = transactionTime;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

