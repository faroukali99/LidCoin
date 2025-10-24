// ============================================
// 2. TransactionWrapper.java
// ============================================
public class TransactionWrapper implements Comparable<TransactionWrapper> {
    String transactionId;
    double fee;
    String sender;
    long timestamp;
    
    public TransactionWrapper(String transactionId, double fee, String sender, long timestamp) {
        this.transactionId = transactionId;
        this.fee = fee;
        this.sender = sender;
        this.timestamp = timestamp;
    }
    
    @Override
    public int compareTo(TransactionWrapper other) {
        int feeComparison = Double.compare(other.fee, this.fee);
        return feeComparison != 0 ? feeComparison : Long.compare(this.timestamp, other.timestamp);
    }
}
