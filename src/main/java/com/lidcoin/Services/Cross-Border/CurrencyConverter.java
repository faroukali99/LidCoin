// ============================================
// 2. Cross-Border - CurrencyConverter.java
// ============================================
package com.lidcoin.crossborder;

import java.util.Map;

public class CurrencyConverter {
    
    public double convert(double amount, String from, String to, 
                         Map<String, ExchangeRate> rates) {
        String key = from + "_" + to;
        ExchangeRate rate = rates.get(key);
        
        if (rate == null) {
            throw new RuntimeException("Taux de change non disponible pour " + key);
        }
        
        return amount * rate.getRate();
    }
    
    public double convertWithFee(double amount, String from, String to,
                                Map<String, ExchangeRate> rates, double feeRate) {
        double converted = convert(amount, from, to, rates);
        double fee = converted * feeRate;
        return converted - fee;
    }
}
