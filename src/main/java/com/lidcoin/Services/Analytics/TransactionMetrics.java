// ============================================
// 2. TransactionMetrics.java
// ============================================
class TransactionMetrics {
    long totalTransactions;
    double totalVolume;
    double averageTransactionSize;
    double maxTransaction;
    double minTransaction;
    Map<String, Long> transactionsByType;
    long firstTransaction;
    long lastTransaction;
    
    public TransactionMetrics() {
        this.transactionsByType = new HashMap<>();
        this.maxTransaction = 0;
        this.minTransaction = Double.MAX_VALUE;
        this.firstTransaction = Instant.now().toEpochMilli();
    }
    
    public void recordTransaction(String type, double amount) {
        totalTransactions++;
        totalVolume += amount;
        averageTransactionSize = totalVolume / totalTransactions;
        transactionsByType.merge(type, 1L, Long::sum);
        
        if (amount > maxTransaction) maxTransaction = amount;
        if (amount < minTransaction) minTransaction = amount;
        lastTransaction = Instant.now().toEpochMilli();
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalTransactions", totalTransactions);
        map.put("totalVolume", totalVolume);
        map.put("averageSize", averageTransactionSize);
        map.put("maxTransaction", maxTransaction);
        map.put("minTransaction", minTransaction);
        map.put("byType", transactionsByType);
        return map;
    }
}
