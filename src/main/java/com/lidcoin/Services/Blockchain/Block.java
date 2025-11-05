package com.lidcoin.blockchain;

import java.security.*;
import java.util.*;
import java.time.Instant;
import java.nio.charset.StandardCharsets;

// ============================================
// 1. Block.java
// ============================================
public class Block {
    private int index;
    private long timestamp;
    private List<Transaction> transactions;
    private String previousHash;
    private String hash;
    private int nonce;
    private String merkleRoot;
    private String validatorAddress;
    private int difficulty;
    private long blockTime;
    
    public Block(int index, List<Transaction> transactions, String previousHash, String validatorAddress) {
        this.index = index;
        this.timestamp = Instant.now().toEpochMilli();
        this.transactions = transactions != null ? transactions : new ArrayList<>();
        this.previousHash = previousHash;
        this.validatorAddress = validatorAddress;
        this.merkleRoot = calculateMerkleRoot();
        this.nonce = 0;
        this.hash = calculateHash();
    }
    
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = index + timestamp + previousHash + merkleRoot + nonce + validatorAddress;
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage", e);
        }
    }
    
    public String calculateMerkleRoot() {
        if (transactions.isEmpty()) return "";
        
        List<String> hashes = new ArrayList<>();
        for (Transaction tx : transactions) {
            hashes.add(tx.getTransactionId());
        }
        
        while (hashes.size() > 1) {
            List<String> newHashes = new ArrayList<>();
            for (int i = 0; i < hashes.size(); i += 2) {
                String combined = hashes.get(i) + (i + 1 < hashes.size() ? hashes.get(i + 1) : hashes.get(i));
                newHashes.add(hashString(combined));
            }
            hashes = newHashes;
        }
        
        return hashes.get(0);
    }
    
    public void mineBlock(int difficulty) {
        long startTime = System.currentTimeMillis();
        String target = new String(new char[difficulty]).replace('\0', '0');
        
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        
        this.blockTime = System.currentTimeMillis() - startTime;
        this.difficulty = difficulty;
        System.out.println("Block miné: " + hash + " (Nonce: " + nonce + ", Temps: " + blockTime + "ms)");
    }
    
    private String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
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
    
    // Getters
    public int getIndex() { return index; }
    public long getTimestamp() { return timestamp; }
    public List<Transaction> getTransactions() { return transactions; }
    public String getPreviousHash() { return previousHash; }
    public String getHash() { return hash; }
    public int getNonce() { return nonce; }
    public String getMerkleRoot() { return merkleRoot; }
    public String getValidatorAddress() { return validatorAddress; }
    public int getDifficulty() { return difficulty; }
    public long getBlockTime() { return blockTime; }
}
