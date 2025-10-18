// ============================================
// 2. Notification.java
// ============================================
class Notification {
    private String id;
    private String userId;
    private NotificationService.NotificationType type;
    private String title;
    private String message;
    private long timestamp;
    private boolean read;
    private Map<String, String> metadata;
    
    public Notification(String userId, NotificationService.NotificationType type, String title, String message) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
        this.read = false;
        this.metadata = new HashMap<>();
    }
    
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public long getTimestamp() { return timestamp; }
    public NotificationService.NotificationType getType() { return type; }
}
