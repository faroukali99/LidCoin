// ============================================
// 13. Validator - ValidatorMetrics.java
// ============================================
package com.lidcoin.validator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ValidatorMetrics {
    private Map<String, Long> validationCounts;
    private Map<String, Long> failureCounts;
    private long totalValidations;
    
    public ValidatorMetrics() {
        this.validationCounts = new ConcurrentHashMap<>();
        this.failureCounts = new ConcurrentHashMap<>();
        this.totalValidations = 0;
    }
    
    public void recordValidation(String validator, boolean success) {
        totalValidations++;
        validationCounts.merge(validator, 1L, Long::sum);
        if (!success) {
            failureCounts.merge(validator, 1L, Long::sum);
        }
    }
    
    public long getValidationCount(String validator) {
        return validationCounts.getOrDefault(validator, 0L);
    }
    
    public long getFailureCount(String validator) {
        return failureCounts.getOrDefault(validator, 0L);
    }
    
    public double getSuccessRate(String validator) {
        long total = getValidationCount(validator);
        long failures = getFailureCount(validator);
        return total > 0 ? (double)(total - failures) / total : 0;
    }
    
    public long getTotalValidations() {
        return totalValidations;
    }
}
