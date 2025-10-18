// ============================================
// 3. TransactionValidator.java
// ============================================
class TransactionValidator {
    private static final double MIN_AMOUNT = 0.0001;
    private static final double MAX_AMOUNT = 1000000.0;
    
    public boolean validate(Transaction transaction) {
        return validateAmount(transaction) 
            && validateAddresses(transaction)
            && validateSignature(transaction)
            && validateTimestamp(transaction);
    }
    
    private boolean validateAmount(Transaction transaction) {
        double amount = transaction.getAmount();
        if (amount < MIN_AMOUNT) {
            System.out.println("Montant trop faible: " + amount);
            return false;
        }
        if (amount > MAX_AMOUNT) {
            System.out.println("Montant trop élevé: " + amount);
            return false;
        }
        return true;
    }
    
    private boolean validateAddresses(Transaction transaction) {
        String sender = transaction.getSender();
        String receiver = transaction.getReceiver();
        
        if (sender == null || sender.isEmpty()) {
            System.out.println("Adresse expéditeur invalide");
            return false;
        }
        
        if (receiver == null || receiver.isEmpty()) {
            System.out.println("Adresse destinataire invalide");
            return false;
        }
        
        if (sender.equals(receiver)) {
            System.out.println("Expéditeur et destinataire identiques");
            return false;
        }
        
        return true;
    }
    
    private boolean validateSignature(Transaction transaction) {
        if (transaction.getSignature() == null || transaction.getSignature().isEmpty()) {
            System.out.println("Signature manquante");
            return false;
        }
        return true;
    }
    
    private boolean validateTimestamp(Transaction transaction) {
        long now = Instant.now().toEpochMilli();
        long txTime = transaction.getTimestamp();
        long diff = Math.abs(now - txTime);
        
        // La transaction ne doit pas être dans le futur ou trop vieille (1 heure)
        if (txTime > now || diff > 3600000) {
            System.out.println("Timestamp invalide");
            return false;
        }
        
        return true;
    }
    
    public boolean validateBalance(Transaction transaction, double senderBalance) {
        double totalRequired = transaction.getAmount() + transaction.getFee();
        if (senderBalance < totalRequired) {
            System.out.println("Solde insuffisant. Requis: " + totalRequired + ", Disponible: " + senderBalance);
            return false;
        }
        return true;
    }
}
