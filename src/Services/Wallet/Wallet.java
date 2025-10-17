import java.security.*;
import java.util.*;
import java.time.Instant;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

// ============================================
// 1. Wallet.java
// ============================================
public class Wallet {
    private String address;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private double balance;
    private long createdAt;
    private boolean locked;
    private List<String> transactionHistory;
    private String encryptedPrivateKey;
    
    public Wallet() {
        generateKeyPair();
        this.balance = 0.0;
        this.createdAt = Instant.now().toEpochMilli();
        this.locked = false;
        this.transactionHistory = new ArrayList<>();
    }
    
    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
            this.address = generateAddress();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de génération de clés", e);
        }
    }
    
    private String generateAddress() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(publicKey.getEncoded());
            return "LC" + bytesToHex(Arrays.copyOfRange(hash, 0, 20));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public void lock() {
        this.locked = true;
    }
    
    public void unlock(String password) {
        // Vérifier le mot de passe et déverrouiller
        this.locked = false;
    }
    
    public void addTransaction(String txId) {
        transactionHistory.add(txId);
    }
    
    // Getters et Setters
    public String getAddress() { return address; }
    public PublicKey getPublicKey() { return publicKey; }
    public PrivateKey getPrivateKey() { return privateKey; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public boolean isLocked() { return locked; }
    public List<String> getTransactionHistory() { return transactionHistory; }
    public long getCreatedAt() { return createdAt; }
}
