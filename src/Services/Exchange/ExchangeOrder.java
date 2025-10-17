// ============================================
// 3. ExchangeOrder.java
// ============================================
class ExchangeOrder implements Comparable<ExchangeOrder> {
    private String orderId;
    private String userId;
    private String pair;
    private ExchangeService.OrderType type;
    private double amount;
    private double price;
    private double filledAmount;
    private OrderStatus status;
    private long timestamp;
    
    public enum OrderStatus {
        OPEN, PARTIALLY_FILLED, FILLED, CANCELLED
    }
    
    public ExchangeOrder(String userId, String pair, ExchangeService.OrderType type, double amount, double price) {
        this.orderId = "ORD" + UUID.randomUUID().toString().substring(0, 16);
        this.userId = userId;
        this.pair = pair;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.filledAmount = 0;
        this.status = OrderStatus.OPEN;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public void fill(double amount) {
        this.filledAmount += amount;
        if (filledAmount >= this.amount) {
            this.status = OrderStatus.FILLED;
        } else {
            this.status = OrderStatus.PARTIALLY_FILLED;
        }
    }
    
    public double getRemainingAmount() {
        return amount - filledAmount;
    }
    
    public boolean isFilled() {
        return status == OrderStatus.FILLED;
    }
    
    @Override
    public int compareTo(ExchangeOrder other) {
        // Pour les ordres d'achat, prix décroissant
        // Pour les ordres de vente, prix croissant
        if (this.type == ExchangeService.OrderType.BUY) {
            return Double.compare(other.price, this.price);
        } else {
            return Double.compare(this.price, other.price);
        }
    }
    
    // Getters
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public String getPair() { return pair; }
    public ExchangeService.OrderType getType() { return type; }
    public double getAmount() { return amount; }
    public double getPrice() { return price; }
    public double getFilledAmount() { return filledAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public long getTimestamp() { return timestamp; }
}
