// ============================================
// 5. SMSChannel.java
// ============================================
class SMSChannel implements NotificationChannel {
    private boolean enabled;
    
    public SMSChannel() {
        this.enabled = true;
    }
    
    @Override
    public void send(Notification notification) {
        System.out.println("📱 SMS envoyé à " + notification.getUserId() + ": " + notification.getTitle());
        // Logique d'envoi de SMS (Twilio)
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public String getChannelName() {
        return "SMS";
    }
}
