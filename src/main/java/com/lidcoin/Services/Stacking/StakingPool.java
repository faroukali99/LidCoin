// ============================================
// 2. StakingPool.java
// ============================================
class StakingPool {
    private String poolType;
    private double apy;
    private int lockPeriodDays;
    private double totalStaked;
    private int stakerCount;
    
    public StakingPool(String poolType, double apy, int lockPeriodDays) {
        this.poolType = poolType;
        this.apy = apy;
        this.lockPeriodDays = lockPeriodDays;
        this.totalStaked = 0;
        this.stakerCount = 0;
    }
    
    public void addStake(double amount) {
        totalStaked += amount;
        stakerCount++;
    }
    
    public void removeStake(double amount) {
        totalStaked -= amount;
        stakerCount--;
    }
    
    public String getPoolType() { return poolType; }
    public double getApy() { return apy; }
    public int getLockPeriodDays() { return lockPeriodDays; }
    public double getTotalStaked() { return totalStaked; }
    public int getStakerCount() { return stakerCount; }
}
