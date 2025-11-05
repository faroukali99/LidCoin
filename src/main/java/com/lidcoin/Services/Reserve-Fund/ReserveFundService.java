// ============================================
// 8. Reserve Fund - ReserveFundService.java
// ============================================
package com.lidcoin.reserve;

import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class ReserveFundService {
    private double reserveBalance;
    private double reserveRatio;
    private StabilityMechanism stabilityMechanism;
    private Map<String, ReserveTransaction> transactions;
    private double totalSupply;
    
    public ReserveFundService(double initialBalance, double reserveRatio) {
        this.reserveBalance = initialBalance;
        this.reserveRatio = reserveRatio;
        this.stabilityMechanism = new StabilityMechanism(reserveRatio);
        this.transactions = new ConcurrentHashMap<>();
        this.totalSupply = 21000000.0;
    }
    
    public void addToReserve(double amount, String source) {
        reserveBalance += amount;
        
        ReserveTransaction tx = new ReserveTransaction(
            "DEPOSIT", amount, source, reserveBalance
        );
        transactions.put(tx.getTransactionId(), tx);
        
        System.out.println("💰 Ajouté au fonds de réserve: " + amount + " LDC");
    }
    
    public boolean withdrawFromReserve(double amount, String destination, String reason) {
        if (amount > reserveBalance) {
            System.out.println("❌ Solde de réserve insuffisant");
            return false;
        }
        
        double minReserve = totalSupply * reserveRatio;
        if (reserveBalance - amount < minReserve) {
            System.out.println("❌ Ne peut pas descendre sous le ratio de réserve minimum");
            return false;
        }
        
        reserveBalance -= amount;
        
        ReserveTransaction tx = new ReserveTransaction(
            "WITHDRAWAL", amount, destination, reserveBalance
        );
        tx.setReason(reason);
        transactions.put(tx.getTransactionId(), tx);
        
        System.out.println("💸 Retiré du fonds de réserve: " + amount + " LDC");
        return true;
    }
    
    public void stabilizePrice(double currentPrice, double targetPrice) {
        stabilityMechanism.stabilize(currentPrice, targetPrice, reserveBalance);
    }
    
    public double getReserveBalance() { return reserveBalance; }
    public double getReserveRatio() { return reserveRatio; }
    public double getMinimumReserve() { return totalSupply * reserveRatio; }
    
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("balance", reserveBalance);
        stats.put("ratio", reserveRatio);
        stats.put("minimumReserve", getMinimumReserve());
        stats.put("utilizationRate", (totalSupply - reserveBalance) / totalSupply);
        return stats;
    }
}
