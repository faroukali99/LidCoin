// ============================================
// 3. NotificationChannel (Interface)
// ============================================
interface NotificationChannel {
    void send(Notification notification);
    boolean isEnabled();
    String getChannelName();
}
