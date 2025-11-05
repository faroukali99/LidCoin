// ============================================
// 9. Reserve Fund - StabilityMechanism.java
// ============================================
package com.lidcoin.reserve;

public class StabilityMechanism {
    private double reserveRatio;
    
    public StabilityMechanism(double reserveRatio) {
        this.reserveRatio = reserveRatio;
    }
    
    public void stabilize(double currentPrice, double targetPrice, double reserveBalance) {
        double deviation = Math.abs(currentPrice - targetPrice) / targetPrice;
        
        if (deviation < 0.01) {
            System.out.println("✅ Prix stable");
            return;
        }
        
        if (currentPrice > targetPrice) {
            // Prix trop élevé - augmenter l'offre
            double increaseAmount = calculateSupplyIncrease(deviation, reserveBalance);
            System.out.println("📈 Augmentation de l'offre: " + increaseAmount);
        } else {
            // Prix trop bas - réduire l'offre
            double decreaseAmount = calculateSupplyDecrease(deviation, reserveBalance);
            System.out.println("📉 Réduction de l'offre: " + decreaseAmount);
        }
    }
    
    private double calculateSupplyIncrease(double deviation, double reserve) {
        return reserve * deviation * 0.1;
    }
    
    private double calculateSupplyDecrease(double deviation, double reserve) {
        return reserve * deviation * 0.1;
    }
}
