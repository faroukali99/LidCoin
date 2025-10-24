// ============================================
// 3. SyncManager.java
// ============================================
class SyncManager {
    
    public void syncNode(NodeState node, long targetHeight) {
        System.out.println("🔄 Synchronisation du node " + node.getNodeId() + " vers hauteur " + targetHeight);
        // Logique de synchronisation
        long currentHeight = node.getBlockHeight();
        long blocksToSync = targetHeight - currentHeight;
        
        System.out.println("📦 " + blocksToSync + " blocs à synchroniser");
    }
}
