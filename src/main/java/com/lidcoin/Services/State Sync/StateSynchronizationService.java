import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. StateSynchronizationService.java
// ============================================
public class StateSynchronizationService {
    private Map<String, NodeState> nodeStates;
    private List<String> connectedNodes;
    private long lastSyncTimestamp;
    private SyncManager syncManager;
    
    public StateSynchronizationService() {
        this.nodeStates = new ConcurrentHashMap<>();
        this.connectedNodes = new ArrayList<>();
        this.lastSyncTimestamp = Instant.now().toEpochMilli();
        this.syncManager = new SyncManager();
    }
    
    public void registerNode(String nodeId, long blockHeight, String lastBlockHash) {
        NodeState state = new NodeState(nodeId, blockHeight, lastBlockHash);
        nodeStates.put(nodeId, state);
        connectedNodes.add(nodeId);
        System.out.println("🔗 Node enregistré: " + nodeId + " (Hauteur: " + blockHeight + ")");
    }
    
    public void updateNodeState(String nodeId, long blockHeight, String lastBlockHash) {
        NodeState state = nodeStates.get(nodeId);
        if (state != null) {
            state.setBlockHeight(blockHeight);
            state.setLastBlockHash(lastBlockHash);
            state.setTimestamp(Instant.now().toEpochMilli());
            state.setSynced(true);
        }
    }
    
    public void synchronizeStates() {
        if (nodeStates.isEmpty()) return;
        
        long maxHeight = nodeStates.values().stream()
            .mapToLong(NodeState::getBlockHeight)
            .max()
            .orElse(0);
        
        for (NodeState state : nodeStates.values()) {
            boolean synced = (state.getBlockHeight() >= maxHeight - 5);
            state.setSynced(synced);
            
            if (!synced) {
                System.out.println("⚠️ Node " + state.getNodeId() + " désynchronisé (hauteur: " + state.getBlockHeight() + ")");
                syncManager.syncNode(state, maxHeight);
            }
        }
        
        lastSyncTimestamp = Instant.now().toEpochMilli();
        System.out.println("✅ Synchronisation terminée - Hauteur max: " + maxHeight);
    }
    
    public List<NodeState> getOutOfSyncNodes() {
        return nodeStates.values().stream()
            .filter(s -> !s.isSynced())
            .toList();
    }
    
    public int getActiveNodeCount() {
        return (int) nodeStates.values().stream()
            .filter(NodeState::isSynced)
            .count();
    }
    
    public NodeState getNodeState(String nodeId) {
        return nodeStates.get(nodeId);
    }
    
    public Map<String, Object> getSyncStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalNodes", nodeStates.size());
        stats.put("syncedNodes", getActiveNodeCount());
        stats.put("lastSync", lastSyncTimestamp);
        return stats;
    }
}
