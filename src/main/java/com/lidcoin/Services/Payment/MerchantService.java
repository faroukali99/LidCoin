// ============================================
// 5. MerchantService.java
// ============================================
class MerchantService {
    private Map<String, Merchant> merchants;
    
    public MerchantService() {
        this.merchants = new ConcurrentHashMap<>();
    }
    
    public String registerMerchant(String businessName, String email) {
        String merchantId = "MRCH" + UUID.randomUUID().toString().substring(0, 12);
        Merchant merchant = new Merchant(merchantId, businessName, email);
        merchants.put(merchantId, merchant);
        System.out.println("🏪 Marchand enregistré: " + businessName);
        return merchantId;
    }
    
    public String createPaymentLink(String merchantId, double amount, String currency) {
        Merchant merchant = merchants.get(merchantId);
        if (merchant == null) {
            throw new RuntimeException("Marchand non trouvé");
        }
        
        String paymentLink = "https://pay.lidcoin.com/" + UUID.randomUUID().toString().substring(0, 8);
        System.out.println("🔗 Lien de paiement créé: " + paymentLink);
        return paymentLink;
    }
    
    private static class Merchant {
        String merchantId;
        String businessName;
        String email;
        String apiKey;
        
        Merchant(String merchantId, String businessName, String email) {
            this.merchantId = merchantId;
            this.businessName = businessName;
            this.email = email;
            this.apiKey = UUID.randomUUID().toString();
        }
    }
}
