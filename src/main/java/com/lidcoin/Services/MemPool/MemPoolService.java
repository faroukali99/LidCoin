import java.util.*;
import java.time.Instant;
import java.util.concurrent.PriorityQueue;

// ============================================
// 1. MemPoolService.java
// ============================================
public class MemPoolService {
    private PriorityQueue<TransactionWrapper> transactionPool;
    private Set<String> processedTransactions;
    private int maxPoolSize;
    private Map<String, Integer> transactionCounts;
    
    public MemPoolService(int maxPoolSize) {
        this.transactionPool = new PriorityQueue<>();
        this.processedTransactions = new HashSet<>();
        this.maxPoolSize = maxPoolSize;
        this.transactionCounts = new HashMap<>();
    }
    
    public synchronized boolean addTransaction(String transactionId, double fee, String sender) {
        if (processedTransactions.contains(transactionId)) {
            System.out.println("Transaction déjà traitée: " + transactionId);
            return false;
        }
        
        if (transactionPool.size() >= maxPoolSize) {
            TransactionWrapper lowest = transactionPool.peek();
            if (lowest != null && fee <= lowest.fee) {
                System.out.println("Pool plein, transaction rejetée (frais trop bas)");
                return false;
            }
            transactionPool.poll();
        }
        
        TransactionWrapper wrapper = new TransactionWrapper(transactionId, fee, sender, Instant.now().toEpochMilli());
        transactionPool.add(wrapper);
        transactionCounts.merge(sender, 1, Integer::sum);
        
        System.out.println("✅ Transaction ajoutée au mempool: " + transactionId);
        return true;
    }
    
    public synchronized List<String> getTopTransactions(int count) {
        List<String> result = new ArrayList<>();
        PriorityQueue<TransactionWrapper> temp = new PriorityQueue<>(transactionPool);
        
        for (int i = 0; i < count && !temp.isEmpty(); i++) {
            TransactionWrapper wrapper = temp.poll();
            result.add(wrapper.transactionId);
            processedTransactions.add(wrapper.transactionId);
        }
        
        return result;
    }
    
    public synchronized void removeTransaction(String transactionId) {
        transactionPool.removeIf(w -> w.transactionId.equals(transactionId));
        processedTransactions.add(transactionId);
    }
    
    public synchronized int getPoolSize() {
        return transactionPool.size();
    }
    
    public synchronized void clear() {
        transactionPool.clear();
    }
    
    public Map<String, Object> getPoolStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("poolSize", transactionPool.size());
        stats.put("processedCount", processedTransactions.size());
        stats.put("maxPoolSize", maxPoolSize);
        return stats;
    }
}
