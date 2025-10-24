// ============================================
// CLASS: NetworkStats
// ============================================
class NetworkStats {
    private long messagesSent;
    private long messagesReceived;
    private long bytesSent;
    private long bytesReceived;
    
    public NetworkStats() {
        this.messagesSent = 0;
        this.messagesReceived = 0;
        this.bytesSent = 0;
        this.bytesReceived = 0;
    }
    
    public void recordSent(long bytes) {
        messagesSent++;
        bytesSent += bytes;
    }
    
    public void recordReceived(long bytes) {
        messagesReceived++;
        bytesReceived += bytes;
    }
    
    public long getMessagesSent() { return messagesSent; }
    public long getMessagesReceived() { return messagesReceived; }
    public long getBytesSent() { return bytesSent; }
    public long getBytesReceived() { return bytesReceived; }
}
