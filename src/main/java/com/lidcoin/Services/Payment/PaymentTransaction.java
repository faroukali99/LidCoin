// ============================================
// 7. PaymentTransaction.java
// ============================================
class PaymentTransaction {
    private String transactionId;
    private String userId;
    private double fiatAmount;
    private double ldcAmount;
    private String currency;
    private String type;
    private PaymentMethod paymentMethod;
    private String status;
    private long timestamp;
    
    public PaymentTransaction(String userId, double fiatAmount, String currency, String type, PaymentMethod method) {
        this.transactionId = "PAY" + UUID.randomUUID().toString().substring(0, 16);
        this.userId = userId;
        this.fiatAmount = fiatAmount;
        this.currency = currency;
        this.type = type;
        this.paymentMethod = method;
        this.status = "PENDING";
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    // Getters et Setters
    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public double getFiatAmount() { return fiatAmount; }
    public void setFiatAmount(double amount) { this.fiatAmount = amount; }
    public double getLdcAmount() { return ldcAmount; }
    public void setLdcAmount(double amount) { this.ldcAmount = amount; }
    public String getCurrency() { return currency; }
    public String getType() { return type; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getTimestamp() { return timestamp; }
}
