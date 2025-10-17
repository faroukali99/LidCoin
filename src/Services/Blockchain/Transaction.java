// ============================================
// Transaction class (référence simplifiée)
// ============================================
class Transaction {
    private String transactionId;
    private String sender;
    private String receiver;
    private double amount;
    private double fee;
    private long timestamp;
    
    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.fee = amount * 0.001;
        this.timestamp = Instant.now().toEpochMilli();
        this.transactionId = generateId();
    }
    
    private String generateId() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = sender + receiver + amount + timestamp;
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString();
        }
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    public String getTransactionId() { return transactionId; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public double getAmount() { return amount; }
    public double getFee() { return fee; }
    public long getTimestamp() { return timestamp; }
}
