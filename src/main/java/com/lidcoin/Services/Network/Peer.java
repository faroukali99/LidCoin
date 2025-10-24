// ============================================
// 3. Peer.java
// ============================================
class Peer {
    private String peerId;
    private String host;
    private int port;
    private boolean active;
    private long lastSeen;
    private int messageCount;
    
    public Peer(String peerId, String host, int port) {
        this.peerId = peerId;
        this.host = host;
        this.port = port;
        this.active = true;
        this.lastSeen = Instant.now().toEpochMilli();
        this.messageCount = 0;
    }
    
    public void updateLastSeen() {
        this.lastSeen = Instant.now().toEpochMilli();
    }
    
    public void incrementMessageCount() {
        this.messageCount++;
    }
    
    public String getPeerId() { return peerId; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
