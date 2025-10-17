// ============================================
// 3. ContractState.java
// ============================================
class ContractState {
    private String contractId;
    private Map<String, Object> state;
    private long lastExecuted;
    private boolean active;
    private int executionCount;
    private List<String> eventLog;
    
    public ContractState(String contractId) {
        this.contractId = contractId;
        this.state = new ConcurrentHashMap<>();
        this.active = true;
        this.executionCount = 0;
        this.eventLog = new ArrayList<>();
    }
    
    public void setState(String key, Object value) {
        state.put(key, value);
    }
    
    public Object getState(String key) {
        return state.get(key);
    }
    
    public void emitEvent(String event) {
        eventLog.add(event);
    }
    
    public void incrementExecutionCount() {
        executionCount++;
    }
    
    // Getters et Setters
    public String getContractId() { return contractId; }
    public Map<String, Object> getState() { return state; }
    public long getLastExecuted() { return lastExecuted; }
    public void setLastExecuted(long time) { this.lastExecuted = time; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getExecutionCount() { return executionCount; }
    public List<String> getEventLog() { return eventLog; }
}
