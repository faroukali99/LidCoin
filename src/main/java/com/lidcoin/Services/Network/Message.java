// ============================================
// 5. Message.java
// ============================================
class Message {
    private String id;
    private String type;
    private String content;
    private long timestamp;
    
    public Message(String type, String content) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.content = content;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public String getId() { return id; }
    public String getType() { return type; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
}
