// ============================================
// 2. SecurityProfile.java
// ============================================
class SecurityProfile {
    private String userId;
    private boolean twoFactorEnabled;
    private String twoFactorSecret;
    private String encryptionLevel;
    private List<String> trustedDevices;
    private int failedLoginAttempts;
    private long lastPasswordChange;
    private long lastLogin;
    private boolean accountLocked;
    private Set<String> activeTokens;
    
    public SecurityProfile(String userId) {
        this.userId = userId;
        this.twoFactorEnabled = false;
        this.encryptionLevel = "AES-256";
        this.trustedDevices = new ArrayList<>();
        this.failedLoginAttempts = 0;
        this.accountLocked = false;
        this.activeTokens = new HashSet<>();
    }
    
    public void incrementFailedLogins() {
        this.failedLoginAttempts++;
    }
    
    public void resetFailedLogins() {
        this.failedLoginAttempts = 0;
    }
    
    public void addTrustedDevice(String device) {
        if (!trustedDevices.contains(device)) {
            trustedDevices.add(device);
        }
    }
    
    // Getters et Setters
    public String getUserId() { return userId; }
    public boolean isTwoFactorEnabled() { return twoFactorEnabled; }
    public void setTwoFactorEnabled(boolean enabled) { this.twoFactorEnabled = enabled; }
    public String getTwoFactorSecret() { return twoFactorSecret; }
    public void setTwoFactorSecret(String secret) { this.twoFactorSecret = secret; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public boolean isAccountLocked() { return accountLocked; }
    public void setAccountLocked(boolean locked) { this.accountLocked = locked; }
    public long getLastLogin() { return lastLogin; }
    public void setLastLogin(long time) { this.lastLogin = time; }
    public List<String> getTrustedDevices() { return trustedDevices; }
}
