import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. CrossBorderService.java
// ============================================
public class CrossBorderService {
    private Map<String, ExchangeRate> exchangeRates;
    private Map<String, String> countryRegulations;
    private CurrencyConverter converter;
    private RegulationChecker regulationChecker;
    
    public CrossBorderService() {
        this.exchangeRates = new ConcurrentHashMap<>();
        this.countryRegulations = new ConcurrentHashMap<>();
        this.converter = new CurrencyConverter();
        this.regulationChecker = new RegulationChecker();
        initializeExchangeRates();
        initializeRegulations();
    }
    
    private void initializeExchangeRates() {
        exchangeRates.put("LDC_USD", new ExchangeRate("LDC", "USD", 1.0));
        exchangeRates.put("LDC_EUR", new ExchangeRate("LDC", "EUR", 0.85));
        exchangeRates.put("LDC_XOF", new ExchangeRate("LDC", "XOF", 655.0));
        exchangeRates.put("LDC_GBP", new ExchangeRate("LDC", "GBP", 0.75));
        exchangeRates.put("LDC_JPY", new ExchangeRate("LDC", "JPY", 110.0));
    }
    
    private void initializeRegulations() {
        countryRegulations.put("US", "Limite: 50000 USD/jour");
        countryRegulations.put("EU", "Limite: 40000 EUR/jour");
        countryRegulations.put("TG", "Limite: 30000000 XOF/jour");
    }
    
    public double convertCurrency(double amount, String from, String to) {
        return converter.convert(amount, from, to, exchangeRates);
    }
    
    public boolean validateCrossBorderTransaction(String fromCountry, String toCountry, double amount) {
        return regulationChecker.validate(fromCountry, toCountry, amount, countryRegulations);
    }
    
    public void updateExchangeRate(String from, String to, double newRate) {
        String key = from + "_" + to;
        exchangeRates.put(key, new ExchangeRate(from, to, newRate));
        System.out.println("Taux de change mis à jour: " + key + " = " + newRate);
    }
    
    public ExchangeRate getExchangeRate(String from, String to) {
        String key = from + "_" + to;
        return exchangeRates.get(key);
    }
    
    public Map<String, ExchangeRate> getAllExchangeRates() {
        return new HashMap<>(exchangeRates);
    }
}
