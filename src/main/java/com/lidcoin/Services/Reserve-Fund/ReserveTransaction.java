// ============================================
// 10. Reserve Fund - ReserveTransaction.java
// ============================================
package com.lidcoin.reserve;

import java.time.Instant;
import java.util.UUID;

public class ReserveTransaction {
    private String transactionId;
    private String type;
    private double amount;
    private String address;
    private double balanceAfter;
    private long timestamp;
    private String reason;
    
    public ReserveTransaction(String type, double amount, String address, double balanceAfter) {
        this.transactionId = "RSV_" + UUID.randomUUID().toString().substring(0, 16);
        this.type = type;
        this.amount = amount;
        this.address = address;
        this.balanceAfter = balanceAfter;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public String getTransactionId() { return transactionId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getAddress() { return address; }
    public double getBalanceAfter() { return balanceAfter; }
    public long getTimestamp() { return timestamp; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
