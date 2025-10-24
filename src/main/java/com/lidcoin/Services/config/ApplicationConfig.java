// ============================================
// SPRING BOOT CONFIGURATION
// Location: src/main/java/com/lidcoin/config/
// ============================================

package com.lidcoin.config;

import org.springframework.context.annotation.*;
import org.springframework.boot.context.properties.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.cache.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

// ============================================
// 1. ApplicationConfig.java - Configuration Principale
// ============================================
@Configuration
@EnableCaching
@EnableScheduling
@EnableAsync
public class ApplicationConfig {
    
    @Bean
    public BlockchainService blockchainService() {
        return new BlockchainService();
    }
    
    @Bean
    public WalletService walletService() {
        return new WalletService();
    }
    
    @Bean
    public TransactionService transactionService() {
        return new TransactionService();
    }
    
    @Bean
    public ConsensusService consensusService() {
        return new ConsensusService(ConsensusService.ConsensusType.PROOF_OF_STAKE);
    }
    
    @Bean
    public ValidatorService validatorService() {
        return new ValidatorService();
    }
    
    @Bean
    public KYCService kycService() {
        return new KYCService();
    }
    
    @Bean
    public AMLCTFService amlctfService() {
        return new AMLCTFService();
    }
    
    @Bean
    public SmartContractEngine smartContractEngine() {
        return new SmartContractEngine();
    }
    
    @Bean
    public ExchangeService exchangeService() {
        return new ExchangeService();
    }
    
    @Bean
    public SecurityService securityService() {
        return new SecurityService();
    }
    
    @Bean
    public APIGateway apiGateway() {
        return new APIGateway();
    }
    
    @Bean
    public AnalyticsService analyticsService() {
        return new AnalyticsService();
    }
    
    @Bean
    public NotificationService notificationService() {
        return new NotificationService();
    }
    
    @Bean
    public CrossBorderService crossBorderService() {
        return new CrossBorderService();
    }
    
    @Bean
    public InteroperabilityService interoperabilityService() {
        return new InteroperabilityService();
    }
    
    @Bean
    public ReserveFundService reserveFundService() {
        return new ReserveFundService(1000000.0, 0.3);
    }
    
    @Bean
    public MemPoolService memPoolService() {
        return new MemPoolService(10000);
    }
    
    @Bean
    public StateSynchronizationService stateSynchronizationService() {
        return new StateSynchronizationService();
    }
    
    @Bean
    public NetworkService networkService() {
        return new NetworkService();
    }
}
