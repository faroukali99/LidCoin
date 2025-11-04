// ============================================
// SERVICE: PAYMENT GATEWAY
// Location: src/main/java/com/lidcoin/payment/
// ============================================

package com.lidcoin.payment;

import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. PaymentGatewayService.java
// ============================================
public class PaymentGatewayService {
    private PaymentProcessor paymentProcessor;
    private FiatOnRampService onRampService;
    private FiatOffRampService offRampService;
    private MerchantService merchantService;
    private Map<String, PaymentTransaction> transactions;
    
    public PaymentGatewayService() {
        this.paymentProcessor = new PaymentProcessor();
        this.onRampService = new FiatOnRampService();
        this.offRampService = new FiatOffRampService();
        this.merchantService = new MerchantService();
        this.transactions = new ConcurrentHashMap<>();
    }
    
    public PaymentTransaction buyLDC(String userId, double fiatAmount, String currency, PaymentMethod method) {
        PaymentTransaction tx = new PaymentTransaction(
            userId,
            fiatAmount,
            currency,
            "BUY",
            method
        );
        
        transactions.put(tx.getTransactionId(), tx);
        
        // Traiter le paiement
        boolean success = paymentProcessor.processPayment(tx);
        
        if (success) {
            double ldcAmount = onRampService.calculateLDCAmount(fiatAmount, currency);
            tx.setLdcAmount(ldcAmount);
            tx.setStatus("COMPLETED");
            System.out.println("✅ Achat de " + ldcAmount + " LDC pour " + fiatAmount + " " + currency);
        } else {
            tx.setStatus("FAILED");
        }
        
        return tx;
    }
    
    public PaymentTransaction sellLDC(String userId, double ldcAmount, String currency, String bankAccount) {
        PaymentTransaction tx = new PaymentTransaction(
            userId,
            0,
            currency,
            "SELL",
            PaymentMethod.BANK_TRANSFER
        );
        
        tx.setLdcAmount(ldcAmount);
        transactions.put(tx.getTransactionId(), tx);
        
        double fiatAmount = offRampService.calculateFiatAmount(ldcAmount, currency);
        tx.setFiatAmount(fiatAmount);
        
        boolean success = offRampService.transferToBank(bankAccount, fiatAmount, currency);
        
        tx.setStatus(success ? "COMPLETED" : "FAILED");
        
        return tx;
    }
    
    public String createMerchantPayment(String merchantId, double amount, String currency) {
        return merchantService.createPaymentLink(merchantId, amount, currency);
    }
    
    public PaymentTransaction getTransaction(String txId) {
        return transactions.get(txId);
    }
}
