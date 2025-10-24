// ============================================
// 4. ConsensusServiceTest.java
// ============================================
package com.lidcoin.consensus;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConsensusServiceTest {
    
    private ConsensusService consensusService;
    
    @BeforeEach
    void setUp() {
        consensusService = new ConsensusService(ConsensusService.ConsensusType.PROOF_OF_STAKE);
    }
    
    @Test
    @DisplayName("Should add validator")
    void testAddValidator() {
        consensusService.addValidator("validator1", 5000.0);
        
        assertEquals(1, consensusService.getValidatorCount());
        assertEquals(5000.0, consensusService.getStake("validator1"));
    }
    
    @Test
    @DisplayName("Should select validator")
    void testSelectValidator() {
        consensusService.addValidator("validator1", 5000.0);
        consensusService.addValidator("validator2", 3000.0);
        
        String selected = consensusService.selectValidator();
        
        assertNotNull(selected);
        assertTrue(consensusService.getValidators().contains(selected));
    }
    
    @Test
    @DisplayName("Should update stake")
    void testUpdateStake() {
        consensusService.addValidator("validator1", 5000.0);
        
        consensusService.updateStake("validator1", 7000.0);
        
        assertEquals(7000.0, consensusService.getStake("validator1"));
    }
    
    @Test
    @DisplayName("Should calculate total stake")
    void testGetTotalStake() {
        consensusService.addValidator("validator1", 5000.0);
        consensusService.addValidator("validator2", 3000.0);
        
        assertEquals(8000.0, consensusService.getTotalStake());
    }
}
