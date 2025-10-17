// ============================================
// 5. EncryptionService.java
// ============================================
class EncryptionService {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    
    public String encrypt(String data, String password) {
        try {
            SecretKeySpec key = generateKey(password);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de chiffrement", e);
        }
    }
    
    public String decrypt(String encryptedData, String password) {
        try {
            SecretKeySpec key = generateKey(password);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de déchiffrement", e);
        }
    }
    
    private SecretKeySpec generateKey(String password) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(key, ALGORITHM);
    }
}
