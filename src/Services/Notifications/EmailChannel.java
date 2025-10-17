// ============================================
// 4. EmailChannel.java
// ============================================
class EmailChannel implements NotificationChannel {
    private boolean enabled;
    
    public EmailChannel() {
        this.enabled = true;
    }
    
    @Override
    public void send(Notification notification) {
        System.out.println("📧 Email envoyé à " + notification.getUserId() + ": " + notification.getTitle());
        // Logique d'envoi d'email (SMTP)
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public String getChannelName() {
        return "EMAIL";
    }
}
