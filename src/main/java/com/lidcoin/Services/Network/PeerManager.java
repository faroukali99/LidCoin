// ============================================
// 2. PeerManager.java
// ============================================
class PeerManager {
    private Map<String, Peer> peers;
    private static final int MAX_PEERS = 50;
    
    public PeerManager() {
        this.peers = new ConcurrentHashMap<>();
    }
    
    public boolean addPeer(String peerId, String host, int port) {
        if (peers.size() >= MAX_PEERS) {
            System.out.println("Limite de peers atteinte");
            return false;
        }
        
        Peer peer = new Peer(peerId, host, port);
        peers.put(peerId, peer);
        return true;
    }
    
    public void removePeer(String peerId) {
        peers.remove(peerId);
    }
    
    public List<Peer> getActivePeers() {
        return peers.values().stream()
            .filter(Peer::isActive)
            .toList();
    }
    
    public int getPeerCount() {
        return peers.size();
    }
    
    public Peer getPeer(String peerId) {
        return peers.get(peerId);
    }
}
