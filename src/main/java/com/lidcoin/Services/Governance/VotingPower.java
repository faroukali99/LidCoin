// ============================================
// 4. VotingPower.java
// ============================================
class VotingPower {
    private Map<String, Double> stakedBalances;
    
    public VotingPower() {
        this.stakedBalances = new ConcurrentHashMap<>();
    }
    
    public double calculatePower(String userId) {
        // Le pouvoir de vote = tokens stakés
        return stakedBalances.getOrDefault(userId, 0.0);
    }
    
    public void updateStakedBalance(String userId, double amount) {
        stakedBalances.put(userId, amount);
    }
}
