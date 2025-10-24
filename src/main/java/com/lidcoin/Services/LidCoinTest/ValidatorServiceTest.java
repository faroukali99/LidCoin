// ============================================
// 9. ValidatorServiceTest.java
// ============================================
package com.lidcoin.validator;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorServiceTest {
    
    private ValidatorService validatorService;
    
    @BeforeEach
    void setUp() {
        validatorService = new ValidatorService();
    }
    
    @Test
    @DisplayName("Should register validator with sufficient stake")
    void testRegisterValidator() {
        validatorService.registerValidator("validator1", 5000.0);
        
        Validator validator = validatorService.getValidator("validator1");
        assertNotNull(validator);
        assertEquals(5000.0, validator.getStake());
    }
    
    @Test
    @DisplayName("Should reject validator with insufficient stake")
    void testInsufficientStake() {
        assertThrows(RuntimeException.class, () -> {
            validatorService.registerValidator("validator1", 500.0);
        });
    }
    
    @Test
    @DisplayName("Should record successful validation")
    void testRecordValidation() {
        validatorService.registerValidator("validator1", 5000.0);
        validatorService.recordValidation("validator1", true);
        
        Validator validator = validatorService.getValidator("validator1");
        assertEquals(1, validator.getBlocksValidated());
    }
    
    @Test
    @DisplayName("Should slash validator")
    void testSlashValidator() {
        validatorService.registerValidator("validator1", 5000.0);
        validatorService.slashValidator("validator1", 1000.0, "Misbehavior");
        
        Validator validator = validatorService.getValidator("validator1");
        assertEquals(4000.0, validator.getStake());
    }
    
    @Test
    @DisplayName("Should get top validators")
    void testGetTopValidators() {
        validatorService.registerValidator("validator1", 5000.0);
        validatorService.registerValidator("validator2", 3000.0);
        validatorService.registerValidator("validator3", 7000.0);
        
        List<Validator> topValidators = validatorService.getTopValidators(2);
        assertEquals(2, topValidators.size());
    }
}
