// ============================================
// 5. UnstakingRequest.java
// ============================================
class UnstakingRequest {
    private String requestId;
    private String stakeId;
    private String userId;
    private double amount;
    private long requestTime;
    private long processingTime;
    private String status;
    private static final long PROCESSING_DELAY = 86400000; // 24 heures
    
    public UnstakingRequest(String stakeId, String userId, double amount) {
        this.requestId = "UNS" + UUID.randomUUID().toString().substring(0, 12);
        this.stakeId = stakeId;
        this.userId = userId;
        this.amount = amount;
        this.requestTime = Instant.now().toEpochMilli();
        this.processingTime = requestTime + PROCESSING_DELAY;
        this.status = "PENDING";
    }
    
    public boolean canProcess() {
        return Instant.now().toEpochMilli() >= processingTime;
    }
    
    public String getRequestId() { return requestId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
