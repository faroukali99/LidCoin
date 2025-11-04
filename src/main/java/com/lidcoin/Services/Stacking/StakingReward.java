// ============================================
// 4. StakingReward.java
// ============================================
class StakingReward {
    private double totalReward;
    private Map<String, Double> rewardsByStake;
    
    public StakingReward() {
        this.rewardsByStake = new HashMap<>();
    }
    
    public void addReward(String stakeId, double reward) {
        rewardsByStake.put(stakeId, reward);
        totalReward += reward;
    }
    
    public double getTotalReward() { return totalReward; }
    public Map<String, Double> getRewardsByStake() { return rewardsByStake; }
}
