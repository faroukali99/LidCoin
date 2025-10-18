// ============================================
// 4. APIResponse.java
// ============================================
class APIResponse {
    private int statusCode;
    private String message;
    private Map<String, Object> data;
    private long timestamp;
    
    public APIResponse(int statusCode, String message, Map<String, Object> data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public Map<String, Object> getData() { return data; }
    public long getTimestamp() { return timestamp; }
}
