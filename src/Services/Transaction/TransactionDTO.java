// ============================================
// 5. TransactionDTO.java
// ============================================
class TransactionDTO {
    private String transactionId;
    private String sender;
    private String receiver;
    private double amount;
    private long timestamp;
    private double fee;
    private String type;
    private String status;
    private int confirmations;
    private String blockHash;
    private Map<String, Object> metadata;
    
    public TransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.sender = transaction.getSender();
        this.receiver = transaction.getReceiver();
        this.amount = transaction.getAmount();
        this.timestamp = transaction.getTimestamp();
        this.fee = transaction.getFee();
        this.type = transaction.getType().name();
        this.status = transaction.getStatus().name();
        this.confirmations = transaction.getConfirmations();
        this.blockHash = transaction.getBlockHash();
        this.metadata = transaction.getMetadata();
    }
    
    // Getters
    public String getTransactionId() { return transactionId; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public double getAmount() { return amount; }
    public long getTimestamp() { return timestamp; }
    public double getFee() { return fee; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public int getConfirmations() { return confirmations; }
    public String getBlockHash() { return blockHash; }
    public Map<String, Object> getMetadata() { return metadata; }
    
    public String toJSON() {
        return String.format(
            "{\"transactionId\":\"%s\",\"sender\":\"%s\",\"receiver\":\"%s\"," +
            "\"amount\":%.8f,\"fee\":%.8f,\"type\":\"%s\",\"status\":\"%s\"," +
            "\"confirmations\":%d,\"timestamp\":%d}",
            transactionId, sender, receiver, amount, fee, type, status, confirmations, timestamp
        );
    }
}
