package com.lidcoin.wallet;

import java.util.*;
import java.util.Base64;

class WalletDTO {
    private String address;
    private double balance;
    private String publicKey;
    private long createdAt;
    private boolean locked;
    private int transactionCount;
    private List<String> recentTransactions;
    
    public WalletDTO(Wallet wallet) {
        this.address = wallet.getAddress();
        this.balance = wallet.getBalance();
        this.publicKey = Base64.getEncoder().encodeToString(wallet.getPublicKey().getEncoded());
        this.createdAt = wallet.getCreatedAt();
        this.locked = wallet.isLocked();
        this.transactionCount = wallet.getTransactionHistory().size();
        this.recentTransactions = wallet.getTransactionHistory().subList(
            Math.max(0, wallet.getTransactionHistory().size() - 10),
            wallet.getTransactionHistory().size()
        );
    }
    
    // Getters
    public String getAddress() { return address; }
    public double getBalance() { return balance; }
    public String getPublicKey() { return publicKey; }
    public long getCreatedAt() { return createdAt; }
    public boolean isLocked() { return locked; }
    public int getTransactionCount() { return transactionCount; }
    public List<String> getRecentTransactions() { return recentTransactions; }
}
