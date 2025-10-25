// ============================================
// 11. LidCoinApplication.java - Classe Principale
// ============================================
package com.lidcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class LidCoinApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LidCoinApplication.class, args);
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│     🪙  LidCoin Blockchain Platform  🪙     │");
        System.out.println("│                                             │");
        System.out.println("│  ✓ Blockchain Service                       │");
        System.out.println("│  ✓ Wallet Management                        │");
        System.out.println("│  ✓ Transaction Processing                   │");
        System.out.println("│  ✓ Smart Contracts                          │");
        System.out.println("│  ✓ Exchange & Trading                       │");
        System.out.println("│  ✓ KYC/AML Compliance                       │");
        System.out.println("│  ✓ Security & 2FA                           │");
        System.out.println("│  ✓ Cross-Border Transactions                │");
        System.out.println("│                                             │");
        System.out.println("│  🌐 API: http://localhost:8080             │");
        System.out.println("│  📚 Docs: http://localhost:8080/swagger-ui │");
        System.out.println("└─────────────────────────────────────────────┘");
    }
}
