// ============================================
// 2. NodeState.java
// ============================================
class NodeState {
    private String nodeId;
    private long blockHeight;
    private String lastBlockHash;
    private long timestamp;
    private boolean synced;
    private int peerCount;
    
    public NodeState(String nodeId, long blockHeight, String lastBlockHash) {
        this.nodeId = nodeId;
        this.blockHeight = blockHeight;
        this.lastBlockHash = lastBlockHash;
        this.timestamp = Instant.now().toEpochMilli();
        this.synced = true;
        this.peerCount = 0;
    }
    
    // Getters et Setters
    public String getNodeId() { return nodeId; }
    public long getBlockHeight() { return blockHeight; }
    public void setBlockHeight(long height) { this.blockHeight = height; }
    public String getLastBlockHash() { return lastBlockHash; }
    public void setLastBlockHash(String hash) { this.lastBlockHash = hash; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long time) { this.timestamp = time; }
    public boolean isSynced() { return synced; }
    public void setSynced(boolean synced) { this.synced = synced; }
    public int getPeerCount() { return peerCount; }
    public void setPeerCount(int count) { this.peerCount = count; }
}
