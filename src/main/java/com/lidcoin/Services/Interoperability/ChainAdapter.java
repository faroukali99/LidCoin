// ============================================
// 7. Interoperability - ChainAdapter.java
// ============================================
package com.lidcoin.interoperability;

public class ChainAdapter {
    private String chainName;
    private String chainSymbol;
    private boolean connected;
    
    public ChainAdapter(String chainName, String chainSymbol) {
        this.chainName = chainName;
        this.chainSymbol = chainSymbol;
        this.connected = true;
    }
    
    public boolean mintTokens(String toAddress, double amount) {
        System.out.println("💎 Minting " + amount + " tokens sur " + chainName + " pour " + toAddress);
        // Simulation - en production, appeler le smart contract de la chaîne
        return true;
    }
    
    public boolean burnTokens(String fromAddress, double amount) {
        System.out.println("🔥 Burning " + amount + " tokens sur " + chainName + " de " + fromAddress);
        return true;
    }
    
    public String getChainName() { return chainName; }
    public String getChainSymbol() { return chainSymbol; }
    public boolean isConnected() { return connected; }
}
