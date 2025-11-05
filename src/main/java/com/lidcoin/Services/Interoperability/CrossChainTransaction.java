// ============================================
// 6. Interoperability - CrossChainTransaction.java
// ============================================
package com.lidcoin.interoperability;

import java.time.Instant;
import java.util.UUID;

public class CrossChainTransaction {
    private String transactionId;
    private String sourceChain;
    private String targetChain;
    private String fromAddress;
    private String toAddress;
    private double amount;
    private String status;
    private long timestamp;
    private String targetTxHash;
    
    public CrossChainTransaction(String sourceChain, String targetChain,
                                String fromAddress, String toAddress, double amount) {
        this.transactionId = "BRIDGE_" + UUID.randomUUID().toString().substring(0, 16);
        this.sourceChain = sourceChain;
        this.targetChain = targetChain;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.status = "PENDING";
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public String getTransactionId() { return transactionId; }
    public String getSourceChain() { return sourceChain; }
    public String getTargetChain() { return targetChain; }
    public String getFromAddress() { return fromAddress; }
    public String getToAddress() { return toAddress; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getTimestamp() { return timestamp; }
    public String getTargetTxHash() { return targetTxHash; }
    public void setTargetTxHash(String hash) { this.targetTxHash = hash; }
}
