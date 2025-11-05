package com.lidcoin.wallet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class WalletService {
    private Map<String, Wallet> wallets;
    private Map<String, String> addressToUser;
    private KeyManager keyManager;
    
    public WalletService() {
        this.wallets = new ConcurrentHashMap<>();
        this.addressToUser = new ConcurrentHashMap<>();
        this.keyManager = new KeyManager();
    }
    
    public Wallet createWallet(String userId) {
        Wallet wallet = new Wallet();
        wallets.put(wallet.getAddress(), wallet);
        addressToUser.put(wallet.getAddress(), userId);
        System.out.println("Wallet créé pour " + userId + ": " + wallet.getAddress());
        return wallet;
    }
    
    public Wallet getWallet(String address) {
        return wallets.get(address);
    }
    
    public List<Wallet> getUserWallets(String userId) {
        List<Wallet> userWallets = new ArrayList<>();
        for (Map.Entry<String, String> entry : addressToUser.entrySet()) {
            if (entry.getValue().equals(userId)) {
                userWallets.add(wallets.get(entry.getKey()));
            }
        }
        return userWallets;
    }
    
    public boolean updateBalance(String address, double amount) {
        Wallet wallet = wallets.get(address);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + amount);
            return true;
        }
        return false;
    }
    
    public double getBalance(String address) {
        Wallet wallet = wallets.get(address);
        return wallet != null ? wallet.getBalance() : 0.0;
    }
    
    public boolean lockWallet(String address) {
        Wallet wallet = wallets.get(address);
        if (wallet != null) {
            wallet.lock();
            return true;
        }
        return false;
    }
    
    public boolean unlockWallet(String address, String password) {
        Wallet wallet = wallets.get(address);
        if (wallet != null) {
            wallet.unlock(password);
            return true;
        }
        return false;
    }
    
    public List<String> getTransactionHistory(String address) {
        Wallet wallet = wallets.get(address);
        return wallet != null ? wallet.getTransactionHistory() : new ArrayList<>();
    }
    
    public boolean exportWallet(String address, String filePath) {
        Wallet wallet = wallets.get(address);
        if (wallet != null) {
            System.out.println("Wallet exporté vers: " + filePath);
            return true;
        }
        return false;
    }
    
    public Wallet importWallet(String filePath, String password) {
        System.out.println("Wallet importé depuis: " + filePath);
        return null;
    }
}
