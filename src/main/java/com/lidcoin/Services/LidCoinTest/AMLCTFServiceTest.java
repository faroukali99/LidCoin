// ============================================
// 6. AMLCTFServiceTest.java
// ============================================
package com.lidcoin.compliance.aml;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AMLCTFServiceTest {
    
    private AMLCTFService amlService;
    
    @BeforeEach
    void setUp() {
        amlService = new AMLCTFService();
    }
    
    @Test
    @DisplayName("Should check transaction")
    void testCheckTransaction() {
        boolean result = amlService.checkTransaction("sender123", "receiver456", 5000.0);
        assertTrue(result);
    }
    
    @Test
    @DisplayName("Should block blacklisted address")
    void testBlacklistedAddress() {
        amlService.addToBlacklist("blacklisted123");
        
        boolean result = amlService.checkTransaction("blacklisted123", "receiver456", 1000.0);
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should create alert for suspicious amount")
    void testSuspiciousAmount() {
        amlService.checkTransaction("sender123", "receiver456", 50000.0);
        
        List<TransactionAlert> alerts = amlService.getAlerts("sender123");
        assertTrue(alerts.size() > 0);
    }
    
    @Test
    @DisplayName("Should verify blacklist status")
    void testIsBlacklisted() {
        amlService.addToBlacklist("suspicious123");
        
        assertTrue(amlService.isBlacklisted("suspicious123"));
        assertFalse(amlService.isBlacklisted("normal123"));
    }
}
