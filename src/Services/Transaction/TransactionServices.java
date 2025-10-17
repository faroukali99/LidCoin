// ============================================
// 2. TransactionService.java
// ============================================
class TransactionService {
    private Map<String, Transaction> transactions;
    private TransactionValidator validator;
    private TransactionPool pool;
    
    public TransactionService() {
        this.transactions = new HashMap<>();
        this.validator = new TransactionValidator();
        this.pool = new TransactionPool();
    }
    
    public Transaction createTransaction(String sender, String receiver, double amount, 
                                        Transaction.TransactionType type, PrivateKey privateKey) {
        Transaction tx = new Transaction(sender, receiver, amount, type);
        tx.signTransaction(privateKey);
        
        if (validator.validate(tx)) {
            transactions.put(tx.getTransactionId(), tx);
            pool.addTransaction(tx);
            System.out.println("Transaction créée: " + tx.getTransactionId());
            return tx;
        }
        
        throw new RuntimeException("Transaction invalide");
    }
    
    public Transaction getTransaction(String txId) {
        return transactions.get(txId);
    }
    
    public List<Transaction> getTransactionsBySender(String sender) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction tx : transactions.values()) {
            if (tx.getSender().equals(sender)) {
                result.add(tx);
            }
        }
        return result;
    }
    
    public List<Transaction> getTransactionsByReceiver(String receiver) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction tx : transactions.values()) {
            if (tx.getReceiver().equals(receiver)) {
                result.add(tx);
            }
        }
        return result;
    }
    
    public List<Transaction> getTransactionsByAddress(String address) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction tx : transactions.values()) {
            if (tx.getSender().equals(address) || tx.getReceiver().equals(address)) {
                result.add(tx);
            }
        }
        return result;
    }
    
    public void confirmTransaction(String txId, String blockHash) {
        Transaction tx = transactions.get(txId);
        if (tx != null) {
            tx.setStatus(Transaction.TransactionStatus.CONFIRMED);
            tx.setBlockHash(blockHash);
            tx.incrementConfirmations();
            pool.removeTransaction(tx);
        }
    }
    
    public void rejectTransaction(String txId, String reason) {
        Transaction tx = transactions.get(txId);
        if (tx != null) {
            tx.setStatus(Transaction.TransactionStatus.REJECTED);
            tx.addMetadata("rejection_reason", reason);
            pool.removeTransaction(tx);
        }
    }
    
    public List<Transaction> getPendingTransactions() {
        return pool.getTransactions();
    }
    
    public double calculateTotalFees(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getFee).sum();
    }
}
