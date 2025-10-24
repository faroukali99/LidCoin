// ============================================
// 10. IntegrationTest.java
// ============================================
package com.lidcoin.integration;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import com.lidcoin.blockchain.*;
import com.lidcoin.wallet.*;
import com.lidcoin.transaction.*;

public class IntegrationTest {
    
    private Blockchain blockchain;
    private WalletService walletService;
    private TransactionService transactionService;
    
    @BeforeEach
    void setUp() {
        blockchain = new Blockchain();
        walletService = new WalletService();
        transactionService = new TransactionService();
    }
    
    @Test
    @DisplayName("Complete transaction flow")
    void testCompleteTransactionFlow() {
        // Create wallets
        Wallet senderWallet = walletService.createWallet("sender");
        Wallet receiverWallet = walletService.createWallet("receiver");
        
        // Initialize balance
        blockchain.getBalances().put(senderWallet.getAddress(), 1000.0);
        walletService.updateBalance(senderWallet.getAddress(), 1000.0);
        
        // Create transaction
        Transaction tx = transactionService.createTransaction(
            senderWallet.getAddress(),
            receiverWallet.getAddress(),
            100.0,
            Transaction.TransactionType.TRANSFER,
            senderWallet.getPrivateKey()
        );
        
        // Add to blockchain
        blockchain.addTransaction(tx);
        
        // Mine block
        blockchain.minePendingTransactions("miner");
        
        // Verify balances
        double senderBalance = blockchain.getBalance(senderWallet.getAddress());
        double receiverBalance = blockchain.getBalance(receiverWallet.getAddress());
        
        assertTrue(senderBalance < 1000.0);
        assertEquals(100.0, receiverBalance);
        assertTrue(blockchain.isChainValid());
    }
    
    @Test
    @DisplayName("Multiple transactions in same block")
    void testMultipleTransactions() {
        Wallet wallet1 = walletService.createWallet("user1");
        Wallet wallet2 = walletService.createWallet("user2");
        Wallet wallet3 = walletService.createWallet("user3");
        
        blockchain.getBalances().put(wallet1.getAddress(), 1000.0);
        
        // Create multiple transactions
        Transaction tx1 = new Transaction(wallet1.getAddress(), wallet2.getAddress(), 100.0);
        Transaction tx2 = new Transaction(wallet1.getAddress(), wallet3.getAddress(), 200.0);
        
        blockchain.addTransaction(tx1);
        blockchain.addTransaction(tx2);
        
        blockchain.minePendingTransactions("miner");
        
        assertEquals(2, blockchain.getChain().get(1).getTransactions().size());
        assertTrue(blockchain.isChainValid());
    }
}
