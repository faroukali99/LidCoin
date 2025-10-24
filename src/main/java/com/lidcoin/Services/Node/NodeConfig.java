// ============================================
// 2. NodeConfig.java
// ============================================
class NodeConfig {
    private int p2pPort;
    private int rpcPort;
    private String dataDir;
    private int maxPeers;
    private boolean mining;
    private String minerAddress;
    
    public NodeConfig() {
        this.p2pPort = 8333;
        this.rpcPort = 8334;
        this.dataDir = "./node-data";
        this.maxPeers = 50;
        this.mining = false;
    }
    
    public int getP2pPort() { return p2pPort; }
    public void setP2pPort(int port) { this.p2pPort = port; }
    public int getRpcPort() { return rpcPort; }
    public void setRpcPort(int port) { this.rpcPort = port; }
    public String getDataDir() { return dataDir; }
    public void setDataDir(String dir) { this.dataDir = dir; }
    public int getMaxPeers() { return maxPeers; }
    public void setMaxPeers(int max) { this.maxPeers = max; }
    public boolean isMining() { return mining; }
    public void setMining(boolean mining) { this.mining = mining; }
    public String getMinerAddress() { return minerAddress; }
    public void setMinerAddress(String address) { this.minerAddress = address; }
}
