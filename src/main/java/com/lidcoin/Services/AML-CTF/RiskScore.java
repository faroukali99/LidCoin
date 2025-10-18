// ============================================
// 4. RiskScore.java
// ============================================
class RiskScore {
    private String address;
    private int score; // 0-100
    private long calculatedAt;
    private RiskLevel level;
    
    public enum RiskLevel {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    
    public RiskScore(String address, int score) {
        this.address = address;
        this.score = Math.min(100, Math.max(0, score));
        this.calculatedAt = Instant.now().toEpochMilli();
        this.level = determineLevel();
    }
    
    private RiskLevel determineLevel() {
        if (score < 30) return RiskLevel.LOW;
        if (score < 60) return RiskLevel.MEDIUM;
        if (score < 85) return RiskLevel.HIGH;
        return RiskLevel.CRITICAL;
    }
    
    // Getters
    public String getAddress() { return address; }
    public int getScore() { return score; }
    public long getCalculatedAt() { return calculatedAt; }
    public RiskLevel getLevel() { return level; }
}
