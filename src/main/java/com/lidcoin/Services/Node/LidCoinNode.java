package com.lidcoin.node;

import java.util.*;
import java.time.Instant;

// ============================================
// 1. LidCoinNode.java
// ============================================
public class LidCoinNode {
    private String nodeId;
    private NodeConfig config;
    private NodeManager manager;
    private boolean running;
    private long startTime;
    private Map<String, Object> nodeStats;
    
    public LidCoinNode(NodeConfig config) {
        this.nodeId = "NODE_" + UUID.randomUUID().toString().substring(0, 8);
        this.config = config;
        this.manager = new NodeManager();
        this.running = false;
        this.nodeStats = new HashMap<>();
    }
    
    public void start() {
        if (running) {
            System.out.println("Node déjà en cours d'exécution");
            return;
        }
        
        running = true;
        startTime = Instant.now().toEpochMilli();
        
        System.out.println("🚀 Démarrage du LidCoin Node: " + nodeId);
        System.out.println("   - Port P2P: " + config.getP2pPort());
        System.out.println("   - Port RPC: " + config.getRpcPort());
        
        manager.initialize(this);
        updateStats();
    }
    
    public void stop() {
        if (!running) {
            System.out.println("Node déjà arrêté");
            return;
        }
        
        running = false;
        manager.shutdown();
        System.out.println("🛑 Node arrêté: " + nodeId);
    }
    
    private void updateStats() {
        nodeStats.put("nodeId", nodeId);
        nodeStats.put("uptime", Instant.now().toEpochMilli() - startTime);
        nodeStats.put("running", running);
        nodeStats.put("p2pPort", config.getP2pPort());
        nodeStats.put("rpcPort", config.getRpcPort());
    }
    
    public Map<String, Object> getStats() {
        updateStats();
        return new HashMap<>(nodeStats);
    }
    
    public String getNodeId() { return nodeId; }
    public boolean isRunning() { return running; }
    public NodeConfig getConfig() { return config; }
}
