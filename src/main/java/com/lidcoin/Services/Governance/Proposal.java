// ============================================
// 2. Proposal.java
// ============================================
class Proposal {
    private String proposalId;
    private String creator;
    private String title;
    private String description;
    private ProposalType type;
    private ProposalStatus status;
    private long createdAt;
    private long votingEndTime;
    private double votesFor;
    private double votesAgainst;
    private Map<String, Object> executionData;
    
    public Proposal(String creator, String title, String description, ProposalType type, long votingPeriod) {
        this.proposalId = "PROP" + UUID.randomUUID().toString().substring(0, 12);
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.type = type;
        this.status = ProposalStatus.ACTIVE;
        this.createdAt = Instant.now().toEpochMilli();
        this.votingEndTime = createdAt + votingPeriod;
        this.votesFor = 0;
        this.votesAgainst = 0;
        this.executionData = new HashMap<>();
    }
    
    public void addVote(boolean support, double power) {
        if (support) {
            votesFor += power;
        } else {
            votesAgainst += power;
        }
    }
    
    public boolean hasEnded() {
        return Instant.now().toEpochMilli() >= votingEndTime;
    }
    
    // Getters et Setters
    public String getProposalId() { return proposalId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ProposalType getType() { return type; }
    public ProposalStatus getStatus() { return status; }
    public void setStatus(ProposalStatus status) { this.status = status; }
    public double getVotesFor() { return votesFor; }
    public double getVotesAgainst() { return votesAgainst; }
    public Map<String, Object> getExecutionData() { return executionData; }
}
