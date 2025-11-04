// ============================================
// 3. FiatOnRampService.java
// ============================================
class FiatOnRampService {
    private Map<String, Double> exchangeRates;
    private double fee = 0.02; // 2%
    
    public FiatOnRampService() {
        this.exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("XOF", 655.0);
    }
    
    public double calculateLDCAmount(double fiatAmount, String currency) {
        double rate = exchangeRates.getOrDefault(currency, 1.0);
        double usdAmount = fiatAmount / rate;
        double feeAmount = usdAmount * fee;
        return usdAmount - feeAmount;
    }
}
