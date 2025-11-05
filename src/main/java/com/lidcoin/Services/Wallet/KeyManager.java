package com.lidcoin.wallet;

import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

class KeyManager {
    private Map<String, byte[]> encryptedKeys;
    private static final String ALGORITHM = "AES";
    
    public KeyManager() {
        this.encryptedKeys = new HashMap<>();
    }
    
    public void storePrivateKey(String address, PrivateKey privateKey, String password) {
        try {
            byte[] encrypted = encryptKey(privateKey.getEncoded(), password);
            encryptedKeys.put(address, encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du stockage de la clé", e);
        }
    }
    
    public PrivateKey retrievePrivateKey(String address, String password) {
        byte[] encrypted = encryptedKeys.get(address);
        if (encrypted == null) {
            return null;
        }
        
        try {
            byte[] decrypted = decryptKey(encrypted, password);
            return KeyFactory.getInstance("RSA").generatePrivate(
                new PKCS8EncodedKeySpec(decrypted)
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la clé", e);
        }
    }
    
    private byte[] encryptKey(byte[] data, String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes(StandardCharsets.UTF_8));
        key = Arrays.copyOf(key, 16);
        
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
    
    private byte[] decryptKey(byte[] encrypted, String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes(StandardCharsets.UTF_8));
        key = Arrays.copyOf(key, 16);
        
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encrypted);
    }
    
    public boolean hasKey(String address) {
        return encryptedKeys.containsKey(address);
    }
    
    public void deleteKey(String address) {
        encryptedKeys.remove(address);
    }
}
