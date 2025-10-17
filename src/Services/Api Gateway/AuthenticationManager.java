// ============================================
// 7. AuthenticationManager.java
// ============================================
class AuthenticationManager {
    private Map<String, String> tokenToUser;
    
    public AuthenticationManager() {
        this.tokenToUser = new ConcurrentHashMap<>();
    }
    
    public boolean validateToken(String token) {
        return token != null && tokenToUser.containsKey(token);
    }
    
    public String extractUserId(String token) {
        return tokenToUser.getOrDefault(token, "anonymous");
    }
    
    public void registerToken(String token, String userId) {
        tokenToUser.put(token, userId);
    }
}
