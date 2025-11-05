package com.lidcoin.security;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Base64;

class TwoFactorAuth {
    private static final int SECRET_LENGTH = 32;
    private SecureRandom random;
    
    public TwoFactorAuth() {
        this.random = new SecureRandom();
    }
    
    public String generateSecret() {
        byte[] buffer = new byte[SECRET_LENGTH];
        random.nextBytes(buffer);
        return Base64.getEncoder().encodeToString(buffer);
    }
    
    public boolean verifyCode(String secret, String code) {
        if (code == null || code.length() != 6) {
            return false;
        }
        
        long timeWindow = Instant.now().toEpochMilli() / 30000;
        String expectedCode = generateTOTP(secret, timeWindow);
        
        return expectedCode.equals(code);
    }
    
    private String generateTOTP(String secret, long timeWindow) {
        try {
            byte[] key = Base64.getDecoder().decode(secret);
            byte[] data = ByteBuffer.allocate(8).putLong(timeWindow).array();
            
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key, "RAW"));
            byte[] hash = mac.doFinal(data);
            
            int offset = hash[hash.length - 1] & 0xf;
            int binary = ((hash[offset] & 0x7f) << 24) |
                        ((hash[offset + 1] & 0xff) << 16) |
                        ((hash[offset + 2] & 0xff) << 8) |
                        (hash[offset + 3] & 0xff);
            
            int otp = binary % 1000000;
            return String.format("%06d", otp);
        } catch (Exception e) {
            throw new RuntimeException("Erreur génération TOTP", e);
        }
    }
}
