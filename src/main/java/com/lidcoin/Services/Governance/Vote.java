// ============================================
// 3. Vote.java
// ============================================
class Vote {
    private String voteId;
    private String userId;
    private String proposalId;
    private boolean support;
    private double votingPower;
    private long timestamp;
    
    public Vote(String userId, String proposalId, boolean support, double votingPower) {
        this.voteId = UUID.randomUUID().toString();
        this.userId = userId;
        this.proposalId = proposalId;
        this.support = support;
        this.votingPower = votingPower;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public boolean isSupport() { return support; }
    public double getVotingPower() { return votingPower; }
}
