// ============================================
// 3. Stake.java
// ============================================
class Stake {
    private String stakeId;
    private String userId;
    private double amount;
    private String poolType;
    private double apy;
    private long stakedAt;
    private long unlockTime;
    private long lastRewardClaim;
    private String status;
    
    public Stake(String userId, double amount, String poolType, double apy, int lockPeriodDays) {
        this.stakeId = "STK" + UUID.randomUUID().toString().substring(0, 12);
        this.userId = userId;
        this.amount = amount;
        this.poolType = poolType;
        this.apy = apy;
        this.stakedAt = Instant.now().toEpochMilli();
        this.unlockTime = stakedAt + (lockPeriodDays * 24L * 60 * 60 * 1000);
        this.lastRewardClaim = stakedAt;
        this.status = "ACTIVE";
    }
    
    public boolean isLocked() {
        return Instant.now().toEpochMilli() < unlockTime;
    }
    
    public void resetLastRewardClaim() {
        this.lastRewardClaim = Instant.now().toEpochMilli();
    }
    
    // Getters
    public String getStakeId() { return stakeId; }
    public String getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getPoolType() { return poolType; }
    public double getApy() { return apy; }
    public long getStakedAt() { return stakedAt; }
    public long getUnlockTime() { return unlockTime; }
    public long getLastRewardClaim() { return lastRewardClaim; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
