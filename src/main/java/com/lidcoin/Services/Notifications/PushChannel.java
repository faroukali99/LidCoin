// ============================================
// 6. PushChannel.java
// ============================================
class PushChannel implements NotificationChannel {
    private boolean enabled;
    
    public PushChannel() {
        this.enabled = true;
    }
    
    @Override
    public void send(Notification notification) {
        System.out.println("🔔 Push notification envoyée à " + notification.getUserId() + ": " + notification.getTitle());
        // Logique de push notification (Firebase)
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public String getChannelName() {
        return "PUSH";
    }
}
