import java.security.*;
import java.util.*;
import java.time.Instant;
import java.nio.charset.StandardCharsets;

// ============================================
// 1. Transaction.java
// ============================================
public class Transaction {
    private String transactionId;
    private String sender;
    private String receiver;
    private double amount;
    private long timestamp;
    private String signature;
    private double fee;
    private TransactionType type;
    private TransactionStatus status;
    private Map<String, Object> metadata;
    private int confirmations;
    private String blockHash;
    
    public enum TransactionType {
        TRANSFER, SMART_CONTRACT, STAKE, UNSTAKE, CROSS_BORDER, REWARD
    }
    
    public enum TransactionStatus {
        PENDING, CONFIRMED, FAILED, REJECTED
    }
    
    public Transaction(String sender, String receiver, double amount, TransactionType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
        this.timestamp = Instant.now().toEpochMilli();
        this.fee = calculateFee();
        this.status = TransactionStatus.PENDING;
        this.metadata = new HashMap<>();
        this.confirmations = 0;
        this.transactionId = calculateTransactionId();
    }
    
    private String calculateTransactionId() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = sender + receiver + amount + timestamp + type;
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    private double calculateFee() {
        double baseFee = 0.001;
        double amountFee = amount * 0.0001;
        return baseFee + amountFee;
    }
    
    public void signTransaction(PrivateKey privateKey) {
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privateKey);
            String data = sender + receiver + amount + timestamp;
            sig.update(data.getBytes(StandardCharsets.UTF_8));
            this.signature = bytesToHex(sig.sign());
        } catch (Exception e) {
            throw new RuntimeException("Erreur de signature", e);
        }
    }
    
    public boolean verifySignature(PublicKey publicKey) {
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);
            String data = sender + receiver + amount + timestamp;
            sig.update(data.getBytes(StandardCharsets.UTF_8));
            return sig.verify(hexToBytes(signature));
        } catch (Exception e) {
            return false;
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
    
    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
    
    public void addMetadata(String key, Object value) {
        metadata.put(key, value);
    }
    
    public void incrementConfirmations() {
        this.confirmations++;
    }
    
    // Getters et Setters
    public String getTransactionId() { return transactionId; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public double getAmount() { return amount; }
    public long getTimestamp() { return timestamp; }
    public String getSignature() { return signature; }
    public double getFee() { return fee; }
    public TransactionType getType() { return type; }
    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
    public Map<String, Object> getMetadata() { return metadata; }
    public int getConfirmations() { return confirmations; }
    public String getBlockHash() { return blockHash; }
    public void setBlockHash(String blockHash) { this.blockHash = blockHash; }
}
