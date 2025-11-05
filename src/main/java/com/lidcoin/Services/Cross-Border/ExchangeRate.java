// ============================================
// 1. Cross-Border - ExchangeRate.java
// ============================================
package com.lidcoin.crossborder;

import java.time.Instant;

public class ExchangeRate {
    private String fromCurrency;
    private String toCurrency;
    private double rate;
    private long timestamp;
    
    public ExchangeRate(String fromCurrency, String toCurrency, double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
        this.timestamp = Instant.now().toEpochMilli();
    }
    
    public String getFromCurrency() { return fromCurrency; }
    public String getToCurrency() { return toCurrency; }
    public double getRate() { return rate; }
    public void setRate(double rate) { 
        this.rate = rate;
        this.timestamp = Instant.now().toEpochMilli();
    }
    public long getTimestamp() { return timestamp; }
}
