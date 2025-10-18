import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. AMLCTFService.java
// ============================================
public class AMLCTFService {
    private Set<String> blacklist;
    private Map<String, List<TransactionAlert>> alerts;
    private Map<String, RiskScore> riskScores;
    private double suspiciousAmountThreshold;
    private RiskScoring riskScoring;
    
    public AMLCTFService() {
        this.blacklist = ConcurrentHashMap.newKeySet();
        this.alerts = new ConcurrentHashMap<>();
        this.riskScores = new ConcurrentHashMap<>();
        this.suspiciousAmountThreshold = 10000.0;
        this.riskScoring = new RiskScoring();
    }
    
    public boolean checkTransaction(String sender, String receiver, double amount) {
        // Vérifier la liste noire
        if (blacklist.contains(sender) || blacklist.contains(receiver)) {
            createAlert(sender, "Adresse sur liste noire", amount, TransactionAlert.AlertLevel.CRITICAL);
            return false;
        }
        
        // Vérifier le montant suspect
        if (amount > suspiciousAmountThreshold) {
            createAlert(sender, "Montant suspect élevé", amount, TransactionAlert.AlertLevel.HIGH);
        }
        
        // Calculer le score de risque
        RiskScore senderRisk = riskScoring.calculateRiskScore(sender, amount);
        if (senderRisk.getScore() > 70) {
            createAlert(sender, "Score de risque élevé", amount, TransactionAlert.AlertLevel.HIGH);
        }
        
        return true;
    }
    
    private void createAlert(String address, String reason, double amount, TransactionAlert.AlertLevel level) {
        TransactionAlert alert = new TransactionAlert(
            UUID.randomUUID().toString(),
            reason,
            amount,
            level
        );
        alerts.computeIfAbsent(address, k -> new ArrayList<>()).add(alert);
        System.out.println("ALERTE AML [" + level + "]: " + reason + " pour " + address);
    }
    
    public void addToBlacklist(String address) {
        blacklist.add(address);
        createAlert(address, "Ajouté à la liste noire", 0, TransactionAlert.AlertLevel.CRITICAL);
    }
    
    public void removeFromBlacklist(String address) {
        blacklist.remove(address);
    }
    
    public boolean isBlacklisted(String address) {
        return blacklist.contains(address);
    }
    
    public List<TransactionAlert> getAlerts(String address) {
        return new ArrayList<>(alerts.getOrDefault(address, new ArrayList<>()));
    }
    
    public List<TransactionAlert> getAllAlerts() {
        List<TransactionAlert> allAlerts = new ArrayList<>();
        for (List<TransactionAlert> addressAlerts : alerts.values()) {
            allAlerts.addAll(addressAlerts);
        }
        return allAlerts;
    }
    
    public RiskScore getRiskScore(String address) {
        return riskScores.getOrDefault(address, new RiskScore(address, 0));
    }
}
