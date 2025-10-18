// ============================================
// 5. RateLimiter.java
// ============================================
class RateLimiter {
    private Map<String, TokenBucket> buckets;
    private int maxRequests;
    private int windowSeconds;
    
    private static class TokenBucket {
        int tokens;
        long lastRefill;
        int capacity;
        
        public TokenBucket(int capacity) {
            this.tokens = capacity;
            this.capacity = capacity;
            this.lastRefill = Instant.now().toEpochMilli();
        }
        
        public boolean tryConsume() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }
        
        private void refill() {
            long now = Instant.now().toEpochMilli();
            long timePassed = now - lastRefill;
            int tokensToAdd = (int) (timePassed / 1000);
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefill = now;
        }
    }
    
    public RateLimiter(int maxRequests, int windowSeconds) {
        this.buckets = new ConcurrentHashMap<>();
        this.maxRequests = maxRequests;
        this.windowSeconds = windowSeconds;
    }
    
    public boolean allowRequest(String userId) {
        TokenBucket bucket = buckets.computeIfAbsent(userId, k -> new TokenBucket(maxRequests));
        return bucket.tryConsume();
    }
}
