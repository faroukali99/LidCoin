// ============================================
// 3. WalletTest.java
// ============================================
package com.lidcoin.wallet;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    
    private WalletService walletService;
    
    @BeforeEach
    void setUp() {
        walletService = new WalletService();
    }
    
    @Test
    @DisplayName("Should create wallet with unique address")
    void testCreateWallet() {
        Wallet wallet = walletService.createWallet("user123");
        
        assertNotNull(wallet);
        assertNotNull(wallet.getAddress());
        assertTrue(wallet.getAddress().startsWith("LC"));
    }
    
    @Test
    @DisplayName("Should update wallet balance")
    void testUpdateBalance() {
        Wallet wallet = walletService.createWallet("user123");
        String address = wallet.getAddress();
        
        walletService.updateBalance(address, 100.0);
        
        assertEquals(100.0, walletService.getBalance(address));
    }
    
    @Test
    @DisplayName("Should lock wallet")
    void testLockWallet() {
        Wallet wallet = walletService.createWallet("user123");
        String address = wallet.getAddress();
        
        walletService.lockWallet(address);
        
        assertTrue(walletService.getWallet(address).isLocked());
    }
    
    @Test
    @DisplayName("Should get user wallets")
    void testGetUserWallets() {
        walletService.createWallet("user123");
        walletService.createWallet("user123");
        
        List<Wallet> wallets = walletService.getUserWallets("user123");
        
        assertEquals(2, wallets.size());
    }
}
