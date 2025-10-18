// ============================================
// 5. ConsensusMetrics.java
// ============================================
class ConsensusMetrics {
    private long totalBlocksProduced;
    private long totalValidations;
    private Map<String, Long> validatorPerformance;
    private Map<String, Double> validatorRewards;
    private double averageBlockTime;
    private long lastBlockTime;
    
    public ConsensusMetrics() {
        this.validatorPerformance = new ConcurrentHashMap<>();
        this.validatorRewards = new ConcurrentHashMap<>();
        this.totalBlocksProduced = 0;
        this.totalValidations = 0;
        this.lastBlockTime = System.currentTimeMillis();
    }
    
    public void recordBlockProduction(String validator, long blockTime) {
        totalBlocksProduced++;
        validatorPerformance.merge(validator, 1L, Long::sum);
        
        // Calculer le temps de bloc moyen
        long timeSinceLastBlock = System.currentTimeMillis() - lastBlockTime;
        averageBlockTime = (averageBlockTime * (totalBlocksProduced - 1) + timeSinceLastBlock) / totalBlocksProduced;
        lastBlockTime = System.currentTimeMillis();
    }
    
    public void recordValidation(String validator) {
        totalValidations++;
    }
    
    public void recordReward(String validator, double reward) {
        validatorRewards.merge(validator, reward, Double::sum);
    }
    
    public long getBlocksProducedBy(String validator) {
        return validatorPerformance.getOrDefault(validator, 0L);
    }
    
    public double getRewardsEarned(String validator) {
        return validatorRewards.getOrDefault(validator, 0.0);
    }
    
    public long getTotalBlocksProduced() {
        return totalBlocksProduced;
    }
    
    public long getTotalValidations() {
        return totalValidations;
    }
    
    public double getAverageBlockTime() {
        return averageBlockTime;
    }
    
    public Map<String, Long> getValidatorPerformance() {
        return new HashMap<>(validatorPerformance);
    }
    
    public Map<String, Double> getValidatorRewards() {
        return new HashMap<>(validatorRewards);
    }
    
    public String getBestValidator() {
        return validatorPerformance.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    
    public void reset() {
        validatorPerformance.clear();
        validatorRewards.clear();
        totalBlocksProduced = 0;
        totalValidations = 0;
        averageBlockTime = 0;
    }
}
