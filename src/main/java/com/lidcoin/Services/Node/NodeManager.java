// ============================================
// 3. NodeManager.java
// ============================================
class NodeManager {
    private boolean initialized;
    
    public NodeManager() {
        this.initialized = false;
    }
    
    public void initialize(LidCoinNode node) {
        System.out.println("⚙️ Initialisation du node manager...");
        
        // Initialiser les composants
        initializeP2P(node);
        initializeRPC(node);
        initializeDatabase(node);
        
        initialized = true;
        System.out.println("✅ Node manager initialisé");
    }
    
    private void initializeP2P(LidCoinNode node) {
        System.out.println("   - Module P2P initialisé sur port " + node.getConfig().getP2pPort());
    }
    
    private void initializeRPC(LidCoinNode node) {
        System.out.println("   - Module RPC initialisé sur port " + node.getConfig().getRpcPort());
    }
    
    private void initializeDatabase(LidCoinNode node) {
        System.out.println("   - Base de données initialisée: " + node.getConfig().getDataDir());
    }
    
    public void shutdown() {
        System.out.println("🔄 Arrêt du node manager...");
        initialized = false;
    }
    
    public boolean isInitialized() {
        return initialized;
    }
}
