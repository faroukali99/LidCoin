import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

// ============================================
// 1. BlockchainTest.java
// ============================================
public class BlockchainTest {
    
    private Blockchain blockchain;
    
    @BeforeEach
    void setUp() {
        blockchain = new Blockchain();
    }
    
    @Test
    @DisplayName("Genesis block should be created")
    void testGenesisBlockCreation() {
        assertNotNull(blockchain.getChain());
        assertEquals(1, blockchain.getChain().size());
        assertEquals(0, blockchain.getLatestBlock().getIndex());
    }
    
    @Test
    @DisplayName("Should add transaction to pending pool")
    void testAddTransaction() {
        // Initialiser un solde
        blockchain.getBalances().put("sender123", 1000.0);
        
        Transaction tx = new Transaction("sender123", "receiver456", 100.0);
        blockchain.addTransaction(tx);
        
        assertEquals(1, blockchain.getPendingTransactions().size());
    }
    
    @Test
    @DisplayName("Should mine pending transactions")
    void testMinePendingTransactions() {
        blockchain.getBalances().put("sender123", 1000.0);
        
        Transaction tx = new Transaction("sender123", "receiver456", 100.0);
        blockchain.addTransaction(tx);
        
        blockchain.minePendingTransactions("miner789");
        
        assertEquals(2, blockchain.getChain().size());
        assertEquals(0, blockchain.getPendingTransactions().size());
    }
    
    @Test
    @DisplayName("Should validate blockchain")
    void testIsChainValid() {
        blockchain.getBalances().put("sender123", 1000.0);
        
        Transaction tx = new Transaction("sender123", "receiver456", 100.0);
        blockchain.addTransaction(tx);
        blockchain.minePendingTransactions("miner789");
        
        assertTrue(blockchain.isChainValid());
    }
    
    @Test
    @DisplayName("Should reject transaction with insufficient balance")
    void testInsufficientBalance() {
        blockchain.getBalances().put("sender123", 10.0);
        
        Transaction tx = new Transaction("sender123", "receiver456", 100.0);
        
        assertThrows(RuntimeException.class, () -> {
            blockchain.addTransaction(tx);
        });
    }
    
    @Test
    @DisplayName("Should update balances after mining")
    void testBalanceUpdate() {
        blockchain.getBalances().put("sender123", 1000.0);
        
        Transaction tx = new Transaction("sender123", "receiver456", 100.0);
        blockchain.addTransaction(tx);
        blockchain.minePendingTransactions("miner789");
        
        double senderBalance = blockchain.getBalance("sender123");
        double receiverBalance = blockchain.getBalance("receiver456");
        double minerBalance = blockchain.getBalance("miner789");
        
        assertTrue(senderBalance < 1000.0);
        assertEquals(100.0, receiverBalance);
        assertEquals(50.0, minerBalance); // Mining reward
    }
}
