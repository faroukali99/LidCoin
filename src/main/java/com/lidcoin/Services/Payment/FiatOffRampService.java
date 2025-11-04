// ============================================
// 4. FiatOffRampService.java
// ============================================
class FiatOffRampService {
    private Map<String, Double> exchangeRates;
    private double fee = 0.02; // 2%
    
    public FiatOffRampService() {
        this.exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("XOF", 655.0);
    }
    
    public double calculateFiatAmount(double ldcAmount, String currency) {
        double rate = exchangeRates.getOrDefault(currency, 1.0);
        double feeAmount = ldcAmount * fee;
        double netLdc = ldcAmount - feeAmount;
        return netLdc * rate;
    }
    
    public boolean transferToBank(String bankAccount, double amount, String currency) {
        System.out.println("🏦 Virement de " + amount + " " + currency + " vers " + bankAccount);
        // Simulation - en production, intégration bancaire réelle
        return true;
    }
}
