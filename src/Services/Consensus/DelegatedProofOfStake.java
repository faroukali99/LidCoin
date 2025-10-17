// ============================================
// 4. DelegatedProofOfStake.java
// ============================================
class DelegatedProofOfStake {
    private List<String> delegates;
    private Map<String, List<String>> delegations; // validator -> delegators
    private Map<String, Double> votingPower;
    private int currentDelegateIndex;
    private static final int MAX_DELEGATES = 21;
    private static final long ROUND_TIME = 30000; // 30 secondes
    private long roundStartTime;
    
    public DelegatedProofOfStake() {
        this.delegates = new ArrayList<>();
        this.delegations = new ConcurrentHashMap<>();
        this.votingPower = new ConcurrentHashMap<>();
        this.currentDelegateIndex = 0;
        this.roundStartTime = System.currentTimeMillis();
    }
    
    public String selectDelegate(List<String> validators) {
        if (delegates.isEmpty()) {
            electDelegates(validators);
        }
        
        long now = System.currentTimeMillis();
        if (now - roundStartTime >= ROUND_TIME) {
            startNewRound();
        }
        
        if (delegates.isEmpty()) {
            return validators.isEmpty() ? null : validators.get(0);
        }
        
        String selectedDelegate = delegates.get(currentDelegateIndex);
        currentDelegateIndex = (currentDelegateIndex + 1) % delegates.size();
        
        return selectedDelegate;
    }
    
    private void electDelegates(List<String> validators) {
        // Trier les validateurs par pouvoir de vote
        List<Map.Entry<String, Double>> sorted = new ArrayList<>(votingPower.entrySet());
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        
        delegates.clear();
        int count = Math.min(MAX_DELEGATES, sorted.size());
        
        for (int i = 0; i < count; i++) {
            delegates.add(sorted.get(i).getKey());
        }
        
        System.out.println("Délégués élus: " + delegates.size());
    }
    
    private void startNewRound() {
        roundStartTime = System.currentTimeMillis();
        currentDelegateIndex = 0;
        System.out.println("Nouveau round DPoS démarré");
    }
    
    public void vote(String delegator, String delegate, double amount) {
        delegations.computeIfAbsent(delegate, k -> new ArrayList<>()).add(delegator);
        votingPower.merge(delegate, amount, Double::sum);
        System.out.println(delegator + " a voté pour " + delegate + " avec " + amount);
    }
    
    public void removeVote(String delegator, String delegate, double amount) {
        List<String> delegators = delegations.get(delegate);
        if (delegators != null) {
            delegators.remove(delegator);
        }
        
        double currentPower = votingPower.getOrDefault(delegate, 0.0);
        votingPower.put(delegate, Math.max(0, currentPower - amount));
    }
    
    public List<String> getDelegates() {
        return new ArrayList<>(delegates);
    }
    
    public double getVotingPower(String delegate) {
        return votingPower.getOrDefault(delegate, 0.0);
    }
    
    public List<String> getDelegators(String delegate) {
        return new ArrayList<>(delegations.getOrDefault(delegate, new ArrayList<>()));
    }
    
    public boolean isDelegate(String address) {
        return delegates.contains(address);
    }
    
    public void reelect(List<String> validators) {
        electDelegates(validators);
    }
}
