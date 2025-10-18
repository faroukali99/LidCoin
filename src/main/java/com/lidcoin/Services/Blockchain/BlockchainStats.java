// ============================================
// 4. BlockchainStats.java
// ============================================
class BlockchainStats {
    private int blockHeight;
    private double circulatingSupply;
    private int pendingTransactions;
    private int difficulty;
    private long averageBlockTime;
    private double hashRate;
    
    public BlockchainStats(int blockHeight, double circulatingSupply, 
                          int pendingTransactions, int difficulty, long averageBlockTime) {
        this.blockHeight = blockHeight;
        this.circulatingSupply = circulatingSupply;
        this.pendingTransactions = pendingTransactions;
        this.difficulty = difficulty;
        this.averageBlockTime = averageBlockTime;
        this.hashRate = calculateHashRate();
    }
    
    private double calculateHashRate() {
        if (averageBlockTime == 0) return 0;
        return Math.pow(2, difficulty) / (averageBlockTime / 1000.0);
    }
    
    // Getters
    public int getBlockHeight() { return blockHeight; }
    public double getCirculatingSupply() { return circulatingSupply; }
    public int getPendingTransactions() { return pendingTransactions; }
    public int getDifficulty() { return difficulty; }
    public long getAverageBlockTime() { return averageBlockTime; }
    public double getHashRate() { return hashRate; }
    
    @Override
    public String toString() {
        return String.format(
            "BlockchainStats{height=%d, supply=%.2f, pending=%d, difficulty=%d, avgBlockTime=%dms, hashRate=%.2f H/s}",
            blockHeight, circulatingSupply, pendingTransactions, difficulty, averageBlockTime, hashRate
        );
    }
}
