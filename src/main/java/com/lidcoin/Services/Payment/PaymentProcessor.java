// ============================================
// 2. PaymentProcessor.java
// ============================================
class PaymentProcessor {
    private Map<PaymentMethod, PaymentProvider> providers;
    
    public PaymentProcessor() {
        this.providers = new HashMap<>();
        providers.put(PaymentMethod.CARD, new StripeProvider());
        providers.put(PaymentMethod.PAYPAL, new PayPalProvider());
        providers.put(PaymentMethod.BANK_TRANSFER, new BankTransferProvider());
    }
    
    public boolean processPayment(PaymentTransaction tx) {
        PaymentProvider provider = providers.get(tx.getPaymentMethod());
        if (provider == null) {
            System.out.println("❌ Provider non supporté: " + tx.getPaymentMethod());
            return false;
        }
        
        return provider.charge(tx);
    }
    
    private interface PaymentProvider {
        boolean charge(PaymentTransaction tx);
    }
    
    private static class StripeProvider implements PaymentProvider {
        public boolean charge(PaymentTransaction tx) {
            System.out.println("💳 Traitement paiement Stripe: " + tx.getFiatAmount() + " " + tx.getCurrency());
            // Simulation - en production, appeler Stripe API
            return true;
        }
    }
    
    private static class PayPalProvider implements PaymentProvider {
        public boolean charge(PaymentTransaction tx) {
            System.out.println("🅿️ Traitement paiement PayPal: " + tx.getFiatAmount() + " " + tx.getCurrency());
            return true;
        }
    }
    
    private static class BankTransferProvider implements PaymentProvider {
        public boolean charge(PaymentTransaction tx) {
            System.out.println("🏦 Traitement virement bancaire: " + tx.getFiatAmount() + " " + tx.getCurrency());
            return true;
        }
    }
}
