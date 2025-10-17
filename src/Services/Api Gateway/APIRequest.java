// ============================================
// 3. APIRequest.java
// ============================================
class APIRequest {
    private String requestId;
    private String endpoint;
    private String method;
    private String userId;
    private long timestamp;
    private int responseCode;
    private long responseTime;
    
    public APIRequest(String endpoint, String method, String userId) {
        this.requestId = UUID.randomUUID().toString();
        this.endpoint = endpoint;
        this.method = method;
        this.userId = userId;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public void setResponseCode(int code) { this.responseCode = code; }
    public void setResponseTime(long time) { this.responseTime = time; }
    
    public String getRequestId() { return requestId; }
    public long getResponseTime() { return responseTime; }
}
