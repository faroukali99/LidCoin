// ============================================
// 2. TransactionAlert.java
// ============================================
class TransactionAlert {
    private String transactionId;
    private String reason;
    private double amount;
    private AlertLevel level;
    private long timestamp;
    private boolean resolved;
    private String resolvedBy;
    
    public enum AlertLevel {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    
    public TransactionAlert(String transactionId, String reason, double amount, AlertLevel level) {
        this.transactionId = transactionId;
        this.reason = reason;
        this.amount = amount;
        this.level = level;
        this.timestamp = Instant.now().toEpochMilli();
        this.resolved = false;
    }
    
    public void resolve(String resolvedBy) {
        this.resolved = true;
        this.resolvedBy = resolvedBy;
    }
    
    // Getters
    public String getTransactionId() { return transactionId; }
    public String getReason() { return reason; }
    public double getAmount() { return amount; }
    public AlertLevel getLevel() { return level; }
    public long getTimestamp() { return timestamp; }
    public boolean isResolved() { return resolved; }
    public String getResolvedBy() { return resolvedBy; }
}
