// ============================================
// SERVICE: STAKING SERVICE
// Location: src/main/java/com/lidcoin/staking/
// ============================================

package com.lidcoin.staking;

import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. StakingService.java
// ============================================
public class StakingService {
    private Map<String, StakingPool> stakingPools;
    private Map<String, List<Stake>> userStakes;
    private RewardCalculator rewardCalculator;
    private Map<String, UnstakingRequest> unstakingRequests;
    
    public StakingService() {
        this.stakingPools = new ConcurrentHashMap<>();
        this.userStakes = new ConcurrentHashMap<>();
        this.rewardCalculator = new RewardCalculator();
        this.unstakingRequests = new ConcurrentHashMap<>();
        initializePools();
    }
    
    private void initializePools() {
        stakingPools.put("FLEXIBLE", new StakingPool("FLEXIBLE", 0.05, 0)); // 5% APY, pas de lock
        stakingPools.put("30_DAYS", new StakingPool("30_DAYS", 0.08, 30)); // 8% APY, 30 jours
        stakingPools.put("90_DAYS", new StakingPool("90_DAYS", 0.12, 90)); // 12% APY, 90 jours
        stakingPools.put("365_DAYS", new StakingPool("365_DAYS", 0.20, 365)); // 20% APY, 1 an
    }
    
    public Stake stake(String userId, double amount, String poolType) {
        StakingPool pool = stakingPools.get(poolType);
        if (pool == null) {
            throw new RuntimeException("Pool de staking invalide");
        }
        
        if (amount < 10.0) {
            throw new RuntimeException("Montant minimum de staking: 10 LDC");
        }
        
        Stake stake = new Stake(userId, amount, poolType, pool.getApy(), pool.getLockPeriodDays());
        userStakes.computeIfAbsent(userId, k -> new ArrayList<>()).add(stake);
        
        pool.addStake(amount);
        
        System.out.println("🔒 Staking de " + amount + " LDC dans pool " + poolType + " (APY: " + (pool.getApy() * 100) + "%)");
        return stake;
    }
    
    public UnstakingRequest requestUnstake(String userId, String stakeId) {
        List<Stake> stakes = userStakes.get(userId);
        if (stakes == null) {
            throw new RuntimeException("Aucun stake trouvé");
        }
        
        Stake stake = stakes.stream()
            .filter(s -> s.getStakeId().equals(stakeId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Stake non trouvé"));
        
        if (stake.isLocked()) {
            throw new RuntimeException("Stake encore verrouillé jusqu'au " + new Date(stake.getUnlockTime()));
        }
        
        UnstakingRequest request = new UnstakingRequest(stakeId, userId, stake.getAmount());
        unstakingRequests.put(request.getRequestId(), request);
        
        stake.setStatus("UNSTAKING");
        
        System.out.println("🔓 Demande de unstaking pour " + stake.getAmount() + " LDC");
        return request;
    }
    
    public void processUnstaking(String requestId) {
        UnstakingRequest request = unstakingRequests.get(requestId);
        if (request != null && request.canProcess()) {
            request.setStatus("COMPLETED");
            System.out.println("✅ Unstaking complété: " + request.getAmount() + " LDC");
        }
    }
    
    public StakingReward calculateRewards(String userId) {
        List<Stake> stakes = userStakes.getOrDefault(userId, new ArrayList<>());
        return rewardCalculator.calculateTotalRewards(stakes);
    }
    
    public void claimRewards(String userId) {
        StakingReward reward = calculateRewards(userId);
        System.out.println("💰 Récompenses réclamées: " + reward.getTotalReward() + " LDC");
        
        // Réinitialiser les récompenses
        List<Stake> stakes = userStakes.get(userId);
        if (stakes != null) {
            stakes.forEach(Stake::resetLastRewardClaim);
        }
    }
    
    public List<Stake> getUserStakes(String userId) {
        return userStakes.getOrDefault(userId, new ArrayList<>());
    }
    
    public Map<String, Object> getStakingStats() {
        Map<String, Object> stats = new HashMap<>();
        double totalStaked = stakingPools.values().stream()
            .mapToDouble(StakingPool::getTotalStaked)
            .sum();
        stats.put("totalStaked", totalStaked);
        stats.put("totalStakers", userStakes.size());
        stats.put("pools", stakingPools.size());
        return stats;
    }
}
