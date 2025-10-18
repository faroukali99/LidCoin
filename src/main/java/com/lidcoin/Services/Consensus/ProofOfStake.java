// ============================================
// 3. ProofOfStake.java
// ============================================
class ProofOfStake {
    private Map<String, Double> stakes;
    private Map<String, Long> lastValidationTime;
    private Map<String, Integer> validationCount;
    private Random random;
    private static final long MINIMUM_STAKE = 1000.0;
    private static final long VALIDATION_COOLDOWN = 60000; // 1 minute
    
    public ProofOfStake(Map<String, Double> stakes) {
        this.stakes = stakes;
        this.lastValidationTime = new ConcurrentHashMap<>();
        this.validationCount = new ConcurrentHashMap<>();
        this.random = new SecureRandom();
    }
    
    public String selectValidator(List<String> validators) {
        if (validators.isEmpty()) return null;
        
        // Filtrer les validateurs éligibles
        List<String> eligible = new ArrayList<>();
        long now = System.currentTimeMillis();
        
        for (String validator : validators) {
            double stake = stakes.getOrDefault(validator, 0.0);
            long lastTime = lastValidationTime.getOrDefault(validator, 0L);
            
            if (stake >= MINIMUM_STAKE && (now - lastTime) >= VALIDATION_COOLDOWN) {
                eligible.add(validator);
            }
        }
        
        if (eligible.isEmpty()) {
            return validators.get(random.nextInt(validators.size()));
        }
        
        // Sélection pondérée par le stake
        return selectByStake(eligible);
    }
    
    private String selectByStake(List<String> validators) {
        double totalStake = 0;
        for (String validator : validators) {
            totalStake += stakes.getOrDefault(validator, 0.0);
        }
        
        double randomValue = random.nextDouble() * totalStake;
        double currentSum = 0;
        
        for (String validator : validators) {
            currentSum += stakes.getOrDefault(validator, 0.0);
            if (currentSum >= randomValue) {
                recordValidation(validator);
                return validator;
            }
        }
        
        return validators.get(validators.size() - 1);
    }
    
    private void recordValidation(String validator) {
        lastValidationTime.put(validator, System.currentTimeMillis());
        validationCount.merge(validator, 1, Integer::sum);
    }
    
    public boolean canValidate(String validator) {
        double stake = stakes.getOrDefault(validator, 0.0);
        long lastTime = lastValidationTime.getOrDefault(validator, 0L);
        long now = System.currentTimeMillis();
        
        return stake >= MINIMUM_STAKE && (now - lastTime) >= VALIDATION_COOLDOWN;
    }
    
    public int getValidationCount(String validator) {
        return validationCount.getOrDefault(validator, 0);
    }
    
    public double getStake(String validator) {
        return stakes.getOrDefault(validator, 0.0);
    }
    
    public Map<String, Integer> getAllValidationCounts() {
        return new HashMap<>(validationCount);
    }
}
