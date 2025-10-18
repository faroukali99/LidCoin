// ============================================
// 2. ExchangePair.java
// ============================================
class ExchangePair {
    private String pair;
    private String baseCurrency;
    private String quoteCurrency;
    private double lastPrice;
    private double high24h;
    private double low24h;
    private double volume24h;
    private double priceChange24h;
    private long lastUpdate;
    
    public ExchangePair(String baseCurrency, String quoteCurrency, double initialPrice) {
        this.pair = baseCurrency + "/" + quoteCurrency;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.lastPrice = initialPrice;
        this.high24h = initialPrice;
        this.low24h = initialPrice;
        this.volume24h = 0;
        this.priceChange24h = 0;
        this.lastUpdate = Instant.now().toEpochMilli();
    }
    
    public void setLastPrice(double price) {
        this.lastPrice = price;
        this.lastUpdate = Instant.now().toEpochMilli();
    }
    
    public void updateStats(double price) {
        if (price > high24h) high24h = price;
        if (price < low24h) low24h = price;
    }
    
    public void addVolume(double volume) {
        this.volume24h += volume;
    }
    
    // Getters
    public String getPair() { return pair; }
    public String getBaseCurrency() { return baseCurrency; }
    public String getQuoteCurrency() { return quoteCurrency; }
    public double getLastPrice() { return lastPrice; }
    public double getHigh24h() { return high24h; }
    public double getLow24h() { return low24h; }
    public double getVolume24h() { return volume24h; }
    public double getPriceChange24h() { return priceChange24h; }
    public long getLastUpdate() { return lastUpdate; }
}
