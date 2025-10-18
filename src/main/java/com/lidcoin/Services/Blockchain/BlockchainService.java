// ============================================
// 3. BlockchainService.java
// ============================================
class BlockchainService {
    private Blockchain blockchain;
    private Map<String, Long> lastBlockTime;
    private static final long BLOCK_TIME_TARGET = 10000; // 10 secondes
    
    public BlockchainService() {
        this.blockchain = new Blockchain();
        this.lastBlockTime = new HashMap<>();
    }
    
    public void addTransaction(Transaction transaction) {
        blockchain.addTransaction(transaction);
    }
    
    public void mineBlock(String minerAddress) {
        long startTime = System.currentTimeMillis();
        blockchain.minePendingTransactions(minerAddress);
        lastBlockTime.put(minerAddress, System.currentTimeMillis() - startTime);
    }
    
    public Block getLatestBlock() {
        return blockchain.getLatestBlock();
    }
    
    public Block getBlock(int index) {
        return blockchain.getBlock(index);
    }
    
    public List<Block> getRecentBlocks(int count) {
        int start = Math.max(0, blockchain.getBlockHeight() - count + 1);
        return blockchain.getBlocks(start, count);
    }
    
    public boolean validateChain() {
        return blockchain.isChainValid();
    }
    
    public double getBalance(String address) {
        return blockchain.getBalance(address);
    }
    
    public int getBlockHeight() {
        return blockchain.getBlockHeight();
    }
    
    public BlockchainStats getStats() {
        return new BlockchainStats(
            blockchain.getBlockHeight(),
            blockchain.getCirculatingSupply(),
            blockchain.getPendingTransactions().size(),
            blockchain.getDifficulty(),
            calculateAverageBlockTime()
        );
    }
    
    private long calculateAverageBlockTime() {
        if (lastBlockTime.isEmpty()) return 0;
        return lastBlockTime.values().stream()
            .mapToLong(Long::longValue)
            .sum() / lastBlockTime.size();
    }
    
    public void adjustDifficulty() {
        long avgBlockTime = calculateAverageBlockTime();
        if (avgBlockTime > 0) {
            if (avgBlockTime < BLOCK_TIME_TARGET * 0.8) {
                blockchain.setDifficulty(blockchain.getDifficulty() + 1);
                System.out.println("Difficulté augmentée: " + blockchain.getDifficulty());
            } else if (avgBlockTime > BLOCK_TIME_TARGET * 1.2) {
                blockchain.setDifficulty(Math.max(1, blockchain.getDifficulty() - 1));
                System.out.println("Difficulté diminuée: " + blockchain.getDifficulty());
            }
        }
    }
}
