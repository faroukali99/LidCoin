// ============================================
// 6. Trade.java
// ============================================
class Trade {
    private String tradeId;
    private String buyer;
    private String seller;
    private String pair;
    private double amount;
    private double price;
    private double fee;
    private long timestamp;
    
    public Trade(String buyer, String seller, String pair, double amount, double price, double feeRate) {
        this.tradeId = "TRD" + UUID.randomUUID().toString().substring(0, 16);
        this.buyer = buyer;
        this.seller = seller;
        this.pair = pair;
        this.amount = amount;
        this.price = price;
        this.fee = amount * price * feeRate;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public double getTotalValue() {
        return amount * price;
    }
    
    // Getters
    public String getTradeId() { return tradeId; }
    public String getBuyer() { return buyer; }
    public String getSeller() { return seller; }
    public String getPair() { return pair; }
    public double getAmount() { return amount; }
    public double getPrice() { return price; }
    public double getFee() { return fee; }
    public long getTimestamp() { return timestamp; }
}
