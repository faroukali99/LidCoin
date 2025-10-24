import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. NetworkService.java
// ============================================
public class NetworkService {
    private PeerManager peerManager;
    private MessageHandler messageHandler;
    private Map<String, NetworkStats> networkStats;
    
    public NetworkService() {
        this.peerManager = new PeerManager();
        this.messageHandler = new MessageHandler();
        this.networkStats = new ConcurrentHashMap<>();
    }
    
    public void connectToPeer(String peerId, String host, int port) {
        peerManager.addPeer(peerId, host, port);
        System.out.println("🌐 Connexion au peer: " + peerId + " @ " + host + ":" + port);
    }
    
    public void disconnectFromPeer(String peerId) {
        peerManager.removePeer(peerId);
        System.out.println("🔌 Déconnexion du peer: " + peerId);
    }
    
    public void broadcastMessage(String messageType, String content) {
        List<Peer> activePeers = peerManager.getActivePeers();
        for (Peer peer : activePeers) {
            sendMessage(peer.getPeerId(), messageType, content);
        }
        System.out.println("📡 Message diffusé à " + activePeers.size() + " peers");
    }
    
    public void sendMessage(String peerId, String messageType, String content) {
        Message message = new Message(messageType, content);
        messageHandler.handleOutgoing(peerId, message);
    }
    
    public List<Peer> getConnectedPeers() {
        return peerManager.getActivePeers();
    }
    
    public int getPeerCount() {
        return peerManager.getPeerCount();
    }
    
    public Map<String, Object> getNetworkStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("peerCount", getPeerCount());
        stats.put("activePeers", peerManager.getActivePeers().size());
        stats.put("messagesSent", messageHandler.getSentCount());
        stats.put("messagesReceived", messageHandler.getReceivedCount());
        return stats;
    }
}
