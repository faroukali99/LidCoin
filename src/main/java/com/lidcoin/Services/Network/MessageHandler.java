// ============================================
// 4. MessageHandler.java
// ============================================
class MessageHandler {
    private int sentCount;
    private int receivedCount;
    
    public MessageHandler() {
        this.sentCount = 0;
        this.receivedCount = 0;
    }
    
    public void handleOutgoing(String peerId, Message message) {
        sentCount++;
        System.out.println("📤 Envoi message à " + peerId + ": " + message.getType());
    }
    
    public void handleIncoming(String peerId, Message message) {
        receivedCount++;
        System.out.println("📥 Message reçu de " + peerId + ": " + message.getType());
    }
    
    public int getSentCount() { return sentCount; }
    public int getReceivedCount() { return receivedCount; }
}
