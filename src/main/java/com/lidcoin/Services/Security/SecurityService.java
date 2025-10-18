import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.nio.charset.StandardCharsets;

// ============================================
// 1. SecurityService.java
// ============================================
public class SecurityService {
    private Map<String, SecurityProfile> profiles;
    private List<SecurityEvent> securityLog;
    private Set<String> suspiciousAddresses;
    private TwoFactorAuth twoFactorAuth;
    private EncryptionService encryptionService;
    private AuditLogger auditLogger;
    
    public SecurityService() {
        this.profiles = new ConcurrentHashMap<>();
        this.securityLog = new ArrayList<>();
        this.suspiciousAddresses = ConcurrentHashMap.newKeySet();
        this.twoFactorAuth = new TwoFactorAuth();
        this.encryptionService = new EncryptionService();
        this.auditLogger = new AuditLogger();
    }
    
    public void enable2FA(String userId) {
        SecurityProfile profile = profiles.computeIfAbsent(userId, SecurityProfile::new);
        String secret = twoFactorAuth.generateSecret();
        profile.setTwoFactorEnabled(true);
        profile.setTwoFactorSecret(secret);
        logSecurityEvent(userId, "2FA_ENABLED", "Authentification à deux facteurs activée", "INFO");
    }
    
    public boolean verify2FA(String userId, String code) {
        SecurityProfile profile = profiles.get(userId);
        if (profile == null || !profile.isTwoFactorEnabled()) {
            return false;
        }
        boolean valid = twoFactorAuth.verifyCode(profile.getTwoFactorSecret(), code);
        if (!valid) {
            logSecurityEvent(userId, "2FA_FAILED", "Code 2FA invalide", "WARNING");
        }
        return valid;
    }
    
    public void recordFailedLogin(String userId, String ipAddress) {
        SecurityProfile profile = profiles.computeIfAbsent(userId, SecurityProfile::new);
        profile.incrementFailedLogins();
        
        if (profile.getFailedLoginAttempts() >= 5) {
            profile.setAccountLocked(true);
            logSecurityEvent(userId, "ACCOUNT_LOCKED", "Compte verrouillé après tentatives échouées", "CRITICAL");
        } else {
            logSecurityEvent(userId, "LOGIN_FAILED", "Tentative de connexion échouée", "WARNING");
        }
        auditLogger.log(userId, "FAILED_LOGIN", ipAddress);
    }
    
    public void recordSuccessfulLogin(String userId, String ipAddress) {
        SecurityProfile profile = profiles.get(userId);
        if (profile != null) {
            profile.resetFailedLogins();
            profile.setLastLogin(Instant.now().toEpochMilli());
            profile.addTrustedDevice(ipAddress);
        }
        auditLogger.log(userId, "SUCCESSFUL_LOGIN", ipAddress);
    }
    
    public String encryptData(String data, String password) {
        return encryptionService.encrypt(data, password);
    }
    
    public String decryptData(String encryptedData, String password) {
        return encryptionService.decrypt(encryptedData, password);
    }
    
    public void logSecurityEvent(String userId, String eventType, String description, String severity) {
        SecurityEvent event = new SecurityEvent(userId, eventType, description, severity);
        securityLog.add(event);
        
        if (severity.equals("CRITICAL") || severity.equals("HIGH")) {
            System.out.println("🚨 ALERTE SÉCURITÉ [" + severity + "]: " + description);
        }
    }
    
    public boolean isAddressSuspicious(String address) {
        return suspiciousAddresses.contains(address);
    }
    
    public void flagSuspiciousAddress(String address, String reason) {
        suspiciousAddresses.add(address);
        logSecurityEvent(address, "ADDRESS_FLAGGED", reason, "HIGH");
    }
    
    public List<SecurityEvent> getSecurityLog(String userId) {
        return securityLog.stream()
            .filter(e -> e.getUserId().equals(userId))
            .toList();
    }
    
    public SecurityProfile getProfile(String userId) {
        return profiles.get(userId);
    }
}
