
// ============================================
// 5. TransactionHistory.java
// ============================================
class TransactionHistory {
    private List<TransactionEntry> transactions;
    private double totalAmount;
    private int count;
    
    private static class TransactionEntry {
        double amount;
        long timestamp;
        
        TransactionEntry(double amount) {
            this.amount = amount;
            this.timestamp = Instant.now().toEpochMilli();
        }
    }
    
    public TransactionHistory() {
        this.transactions = new ArrayList<>();
        this.totalAmount = 0;
        this.count = 0;
    }
    
    public void addTransaction(double amount) {
        transactions.add(new TransactionEntry(amount));
        totalAmount += amount;
        count++;
    }
    
    public int getTransactionCount() {
        return count;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public double getAverageAmount() {
        return count > 0 ? totalAmount / count : 0;
    }
    
    public int getRecentTransactionCount(long timeWindow) {
        long now = Instant.now().toEpochMilli();
        int recentCount = 0;
        
        for (TransactionEntry entry : transactions) {
            if ((now - entry.timestamp) <= timeWindow) {
                recentCount++;
            }
        }
        
        return recentCount;
    }
    
    public double getRecentTotalAmount(long timeWindow) {
        long now = Instant.now().toEpochMilli();
        double recentTotal = 0;
        
        for (TransactionEntry entry : transactions) {
            if ((now - entry.timestamp) <= timeWindow) {
                recentTotal += entry.amount;
            }
        }
        
        return recentTotal;
    }
}
