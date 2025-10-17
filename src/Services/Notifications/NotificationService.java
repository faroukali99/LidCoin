import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. NotificationService.java
// ============================================
public class NotificationService {
    private Map<String, List<Notification>> userNotifications;
    private List<NotificationChannel> channels;
    
    public enum NotificationType {
        TRANSACTION, SECURITY_ALERT, KYC_UPDATE, SYSTEM, MARKETING, PRICE_ALERT
    }
    
    public NotificationService() {
        this.userNotifications = new ConcurrentHashMap<>();
        this.channels = new ArrayList<>();
        initializeChannels();
    }
    
    private void initializeChannels() {
        channels.add(new EmailChannel());
        channels.add(new SMSChannel());
        channels.add(new PushChannel());
    }
    
    public void sendNotification(String userId, NotificationType type, String title, String message) {
        Notification notification = new Notification(userId, type, title, message);
        userNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
        
        // Envoyer via tous les canaux activés
        for (NotificationChannel channel : channels) {
            if (channel.isEnabled()) {
                channel.send(notification);
            }
        }
        
        System.out.println("📬 Notification envoyée à " + userId + ": " + title);
    }
    
    public List<Notification> getUserNotifications(String userId, boolean unreadOnly) {
        List<Notification> notifications = userNotifications.getOrDefault(userId, new ArrayList<>());
        if (unreadOnly) {
            return notifications.stream().filter(n -> !n.isRead()).toList();
        }
        return new ArrayList<>(notifications);
    }
    
    public void markAsRead(String notificationId) {
        for (List<Notification> notifications : userNotifications.values()) {
            for (Notification notification : notifications) {
                if (notification.getId().equals(notificationId)) {
                    notification.setRead(true);
                    return;
                }
            }
        }
    }
    
    public void markAllAsRead(String userId) {
        List<Notification> notifications = userNotifications.get(userId);
        if (notifications != null) {
            notifications.forEach(n -> n.setRead(true));
        }
    }
    
    public int getUnreadCount(String userId) {
        List<Notification> notifications = userNotifications.getOrDefault(userId, new ArrayList<>());
        return (int) notifications.stream().filter(n -> !n.isRead()).count();
    }
}
