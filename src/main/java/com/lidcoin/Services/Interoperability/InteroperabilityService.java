// ============================================
// 4. Interoperability - InteroperabilityService.java
// ============================================
package com.lidcoin.interoperability;

import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class InteroperabilityService {
    private BridgeService bridgeService;
    private Map<String, ChainAdapter> chainAdapters;
    private Map<String, CrossChainTransaction> transactions;
    
    public InteroperabilityService() {
        this.bridgeService = new BridgeService();
        this.chainAdapters = new ConcurrentHashMap<>();
        this.transactions = new ConcurrentHashMap<>();
        initializeAdapters();
    }
    
    private void initializeAdapters() {
        chainAdapters.put("ETH", new ChainAdapter("Ethereum", "ETH"));
        chainAdapters.put("BTC", new ChainAdapter("Bitcoin", "BTC"));
        chainAdapters.put("BNB", new ChainAdapter("Binance Smart Chain", "BNB"));
    }
    
    public CrossChainTransaction bridgeToChain(String targetChain, String fromAddress, 
                                              String toAddress, double amount) {
        ChainAdapter adapter = chainAdapters.get(targetChain);
        if (adapter == null) {
            throw new RuntimeException("Blockchain non supportée: " + targetChain);
        }
        
        CrossChainTransaction tx = new CrossChainTransaction(
            "LDC", targetChain, fromAddress, toAddress, amount
        );
        
        transactions.put(tx.getTransactionId(), tx);
        
        boolean success = bridgeService.bridge(tx, adapter);
        tx.setStatus(success ? "COMPLETED" : "FAILED");
        
        System.out.println("🌉 Bridge vers " + targetChain + ": " + amount + " LDC");
        return tx;
    }
    
    public CrossChainTransaction getTransaction(String txId) {
        return transactions.get(txId);
    }
    
    public List<String> getSupportedChains() {
        return new ArrayList<>(chainAdapters.keySet());
    }
}
