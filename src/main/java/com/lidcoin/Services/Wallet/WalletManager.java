package com.lidcoin.wallet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class WalletManager {
    private WalletService walletService;
    private Map<String, Set<String>> userWallets;
    private static final int MAX_WALLETS_PER_USER = 10;
    
    public WalletManager(WalletService walletService) {
        this.walletService = walletService;
        this.userWallets = new ConcurrentHashMap<>();
    }
    
    public Wallet createUserWallet(String userId, String password) {
        Set<String> wallets = userWallets.getOrDefault(userId, new HashSet<>());
        
        if (wallets.size() >= MAX_WALLETS_PER_USER) {
            throw new RuntimeException("Limite de wallets atteinte pour l'utilisateur");
        }
        
        Wallet wallet = walletService.createWallet(userId);
        wallets.add(wallet.getAddress());
        userWallets.put(userId, wallets);
        
        return wallet;
    }
    
    public List<Wallet> getAllUserWallets(String userId) {
        return walletService.getUserWallets(userId);
    }
    
    public double getTotalBalance(String userId) {
        List<Wallet> wallets = getAllUserWallets(userId);
        return wallets.stream().mapToDouble(Wallet::getBalance).sum();
    }
    
    public boolean deleteWallet(String userId, String address) {
        Set<String> wallets = userWallets.get(userId);
        if (wallets != null && wallets.contains(address)) {
            wallets.remove(address);
            return true;
        }
        return false;
    }
    
    public Wallet getPrimaryWallet(String userId) {
        List<Wallet> wallets = getAllUserWallets(userId);
        return wallets.isEmpty() ? null : wallets.get(0);
    }
}
