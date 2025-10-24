// ============================================
// 2. TransactionTest.java
// ============================================
package com.lidcoin.transaction;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.security.*;

public class TransactionTest {
    
    private KeyPair keyPair;
    
    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        keyPair = keyGen.generateKeyPair();
    }
    
    @Test
    @DisplayName("Should create transaction with valid data")
    void testCreateTransaction() {
        Transaction tx = new Transaction("sender123", "receiver456", 100.0, Transaction.TransactionType.TRANSFER);
        
        assertNotNull(tx.getTransactionId());
        assertEquals("sender123", tx.getSender());
        assertEquals("receiver456", tx.getReceiver());
        assertEquals(100.0, tx.getAmount());
    }
    
    @Test
    @DisplayName("Should calculate transaction fee")
    void testCalculateFee() {
        Transaction tx = new Transaction("sender123", "receiver456", 100.0, Transaction.TransactionType.TRANSFER);
        
        assertTrue(tx.getFee() > 0);
        assertTrue(tx.getFee() < 1.0);
    }
    
    @Test
    @DisplayName("Should sign transaction")
    void testSignTransaction() {
        Transaction tx = new Transaction("sender123", "receiver456", 100.0, Transaction.TransactionType.TRANSFER);
        tx.signTransaction(keyPair.getPrivate());
        
        assertNotNull(tx.getSignature());
        assertTrue(tx.getSignature().length() > 0);
    }
    
    @Test
    @DisplayName("Should verify transaction signature")
    void testVerifySignature() {
        Transaction tx = new Transaction("sender123", "receiver456", 100.0, Transaction.TransactionType.TRANSFER);
        tx.signTransaction(keyPair.getPrivate());
        
        assertTrue(tx.verifySignature(keyPair.getPublic()));
    }
    
    @Test
    @DisplayName("Should reject invalid signature")
    void testInvalidSignature() throws NoSuchAlgorithmException {
        Transaction tx = new Transaction("sender123", "receiver456", 100.0, Transaction.TransactionType.TRANSFER);
        tx.signTransaction(keyPair.getPrivate());
        
        // Generate different key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair wrongKeyPair = keyGen.generateKeyPair();
        
        assertFalse(tx.verifySignature(wrongKeyPair.getPublic()));
    }
}
