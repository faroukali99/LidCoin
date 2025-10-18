// ============================================
// 4. TransactionPool.java
// ============================================
class TransactionPool {
    private List<Transaction> pendingTransactions;
    private Map<String, Transaction> transactionMap;
    private static final int MAX_POOL_SIZE = 10000;
    
    public TransactionPool() {
        this.pendingTransactions = new ArrayList<>();
        this.transactionMap = new HashMap<>();
    }
    
    public synchronized boolean addTransaction(Transaction transaction) {
        if (pendingTransactions.size() >= MAX_POOL_SIZE) {
            System.out.println("Pool de transactions plein");
            return false;
        }
        
        if (transactionMap.containsKey(transaction.getTransactionId())) {
            System.out.println("Transaction déjà dans le pool");
            return false;
        }
        
        pendingTransactions.add(transaction);
        transactionMap.put(transaction.getTransactionId(), transaction);
        return true;
    }
    
    public synchronized void removeTransaction(Transaction transaction) {
        pendingTransactions.remove(transaction);
        transactionMap.remove(transaction.getTransactionId());
    }
    
    public synchronized List<Transaction> getTransactions() {
        return new ArrayList<>(pendingTransactions);
    }
    
    public synchronized List<Transaction> getTopTransactions(int count) {
        // Trier par frais décroissants
        List<Transaction> sorted = new ArrayList<>(pendingTransactions);
        sorted.sort((t1, t2) -> Double.compare(t2.getFee(), t1.getFee()));
        return sorted.subList(0, Math.min(count, sorted.size()));
    }
    
    public synchronized Transaction getTransaction(String txId) {
        return transactionMap.get(txId);
    }
    
    public synchronized int size() {
        return pendingTransactions.size();
    }
    
    public synchronized void clear() {
        pendingTransactions.clear();
        transactionMap.clear();
    }
    
    public synchronized List<Transaction> getTransactionsByType(Transaction.TransactionType type) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction tx : pendingTransactions) {
            if (tx.getType() == type) {
                result.add(tx);
            }
        }
        return result;
    }
    
    public synchronized double getTotalValue() {
        return pendingTransactions.stream().mapToDouble(Transaction::getAmount).sum();
    }
    
    public synchronized double getTotalFees() {
        return pendingTransactions.stream().mapToDouble(Transaction::getFee).sum();
    }
}
