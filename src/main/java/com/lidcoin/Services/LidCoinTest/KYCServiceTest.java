// ============================================
// 5. KYCServiceTest.java
// ============================================
package com.lidcoin.kyc;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class KYCServiceTest {
    
    private KYCService kycService;
    
    @BeforeEach
    void setUp() {
        kycService = new KYCService();
    }
    
    @Test
    @DisplayName("Should submit KYC")
    void testSubmitKYC() {
        kycService.submitKYC("user123", "John Doe", "ID123", "john@example.com", "123 Street", "+1234567890");
        
        KYCRecord record = kycService.getKYCRecord("user123");
        assertNotNull(record);
        assertEquals("John Doe", record.getFullName());
        assertEquals(KYCRecord.KYCStatus.PENDING, record.getStatus());
    }
    
    @Test
    @DisplayName("Should approve KYC")
    void testApproveKYC() {
        kycService.submitKYC("user123", "John Doe", "ID123", "john@example.com", "123 Street", "+1234567890");
        kycService.verifyKYC("user123", true, "admin");
        
        assertTrue(kycService.isKYCApproved("user123"));
    }
    
    @Test
    @DisplayName("Should reject KYC")
    void testRejectKYC() {
        kycService.submitKYC("user123", "John Doe", "ID123", "john@example.com", "123 Street", "+1234567890");
        kycService.verifyKYC("user123", false, "admin");
        
        assertFalse(kycService.isKYCApproved("user123"));
    }
    
    @Test
    @DisplayName("Should get pending KYC records")
    void testGetPendingKYC() {
        kycService.submitKYC("user1", "John Doe", "ID1", "john@example.com", "123 St", "+1234567890");
        kycService.submitKYC("user2", "Jane Doe", "ID2", "jane@example.com", "456 St", "+0987654321");
        
        List<KYCRecord> pending = kycService.getPendingKYC();
        assertEquals(2, pending.size());
    }
}
