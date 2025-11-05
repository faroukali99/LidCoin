package com.lidcoin.staking;

import java.util.*;
import java.time.Instant;

class RewardCalculator {
    
    public StakingReward calculateTotalRewards(List<Stake> stakes) {
        StakingReward totalReward = new StakingReward();
        
        for (Stake stake : stakes) {
            if (stake.getStatus().equals("ACTIVE")) {
                double reward = calculateReward(stake);
                totalReward.addReward(stake.getStakeId(), reward);
            }
        }
        
        return totalReward;
    }
    
    private double calculateReward(Stake stake) {
        long now = Instant.now().toEpochMilli();
        long stakingDuration = now - stake.getLastRewardClaim();
        
        // Calcul des récompenses: (amount * APY * durée) / année
        double yearInMs = 365.25 * 24 * 60 * 60 * 1000;
        double reward = (stake.getAmount() * stake.getApy() * stakingDuration) / yearInMs;
        
        return reward;
    }
}
