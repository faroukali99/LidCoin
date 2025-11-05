// ============================================
// 12. Validator - Validator.java
// ============================================
package com.lidcoin.validator;

import java.time.Instant;

public class Validator {
    private String address;
    private double stake;
    private int blocksValidated;
    private int failedValidations;
    private double totalRewards;
    private long registeredAt;
    private boolean active;
    private int slashCount;
    
    public Validator(String address, double stake) {
        this.address = address;
        this.stake = stake;
        this.blocksValidated = 0;
        this.failedValidations = 0;
        this.totalRewards = 0;
        this.registeredAt = Instant.now().toEpochMilli();
        this.active = true;
        this.slashCount = 0;
    }
    
    public void incrementBlocksValidated() {
        this.blocksValidated++;
    }
    
    public void incrementFailedValidations() {
        this.failedValidations++;
    }
    
    public void incrementSlashCount() {
        this.slashCount++;
    }
    
    public void addReward(double reward) {
        this.totalRewards += reward;
    }
    
    public double getSuccessRate() {
        int total = blocksValidated + failedValidations;
        return total > 0 ? (double) blocksValidated / total : 0;
    }
    
    // Getters and Setters
    public String getAddress() { return address; }
    public double getStake() { return stake; }
    public void setStake(double stake) { this.stake = stake; }
    public int getBlocksValidated() { return blocksValidated; }
    public int getFailedValidations() { return failedValidations; }
    public double getTotalRewards() { return totalRewards; }
    public long getRegisteredAt() { return registeredAt; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getSlashCount() { return slashCount; }
}
