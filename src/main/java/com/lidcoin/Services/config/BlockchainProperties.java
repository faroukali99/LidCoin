// ============================================
// 2. BlockchainProperties.java - Configuration Properties
// ============================================
@Configuration
@ConfigurationProperties(prefix = "lidcoin.blockchain")
public class BlockchainProperties {
    
    private int difficulty = 4;
    private double miningReward = 50.0;
    private long blockTime = 10000;
    private int maxTransactionsPerBlock = 1000;
    private double totalSupply = 21000000.0;
    
    // Getters et Setters
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    
    public double getMiningReward() { return miningReward; }
    public void setMiningReward(double miningReward) { this.miningReward = miningReward; }
    
    public long getBlockTime() { return blockTime; }
    public void setBlockTime(long blockTime) { this.blockTime = blockTime; }
    
    public int getMaxTransactionsPerBlock() { return maxTransactionsPerBlock; }
    public void setMaxTransactionsPerBlock(int max) { this.maxTransactionsPerBlock = max; }
    
    public double getTotalSupply() { return totalSupply; }
    public void setTotalSupply(double totalSupply) { this.totalSupply = totalSupply; }
}
