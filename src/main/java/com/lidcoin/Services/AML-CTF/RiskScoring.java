// ============================================
// 3. RiskScoring.java
// ============================================
class RiskScoring {
    private Map<String, TransactionHistory> transactionHistories;
    
    public RiskScoring() {
        this.transactionHistories = new ConcurrentHashMap<>();
    }
    
    public RiskScore calculateRiskScore(String address, double amount) {
        TransactionHistory history = transactionHistories.computeIfAbsent(
            address, k -> new TransactionHistory()
        );
        
        history.addTransaction(amount);
        
        int score = 0;
        
        // Fréquence des transactions
        if (history.getTransactionCount() > 100) score += 20;
        
        // Montant moyen
        if (history.getAverageAmount() > 50000) score += 30;
        
        // Transactions récentes
        if (history.getRecentTransactionCount(3600000) > 10) score += 25; // 10 tx dans la dernière heure
        
        // Montant total
        if (history.getTotalAmount() > 1000000) score += 25;
        
        return new RiskScore(address, score);
    }
    
    public TransactionHistory getHistory(String address) {
        return transactionHistories.get(address);
    }
}
