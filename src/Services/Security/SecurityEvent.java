// ============================================
// 3. SecurityEvent.java
// ============================================
class SecurityEvent {
    private String eventId;
    private String userId;
    private String eventType;
    private String description;
    private String severity;
    private long timestamp;
    private String ipAddress;
    
    public SecurityEvent(String userId, String eventType, String description, String severity) {
        this.eventId = UUID.randomUUID().toString();
        this.userId = userId;
        this.eventType = eventType;
        this.description = description;
        this.severity = severity;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public String getUserId() { return userId; }
    public String getEventType() { return eventType; }
    public String getDescription() { return description; }
    public String getSeverity() { return severity; }
    public long getTimestamp() { return timestamp; }
}
