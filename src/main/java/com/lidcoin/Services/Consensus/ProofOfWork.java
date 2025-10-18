// ============================================
// 2. ProofOfWork.java
// ============================================
class ProofOfWork {
    private Map<String, Long> minerHashRates;
    private Map<String, Integer> blocksMinedCount;
    
    public ProofOfWork() {
        this.minerHashRates = new ConcurrentHashMap<>();
        this.blocksMinedCount = new ConcurrentHashMap<>();
    }
    
    public String selectMiner(List<String> miners) {
        if (miners.isEmpty()) return null;
        
        // Sélection basée sur la puissance de calcul
        String bestMiner = miners.get(0);
        long bestHashRate = minerHashRates.getOrDefault(bestMiner, 1000L);
        
        for (String miner : miners) {
            long hashRate = minerHashRates.getOrDefault(miner, 1000L);
            if (hashRate > bestHashRate) {
                bestHashRate = hashRate;
                bestMiner = miner;
            }
        }
        
        return bestMiner;
    }
    
    public boolean validateWork(String blockHash, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return blockHash.substring(0, difficulty).equals(target);
    }
    
    public void recordMining(String miner, long hashRate) {
        minerHashRates.put(miner, hashRate);
        blocksMinedCount.merge(miner, 1, Integer::sum);
    }
    
    public long getHashRate(String miner) {
        return minerHashRates.getOrDefault(miner, 0L);
    }
    
    public int getBlocksMinedCount(String miner) {
        return blocksMinedCount.getOrDefault(miner, 0);
    }
    
    public Map<String, Long> getAllHashRates() {
        return new HashMap<>(minerHashRates);
    }
    
    public long getTotalNetworkHashRate() {
        return minerHashRates.values().stream().mapToLong(Long::longValue).sum();
    }
}
