// ============================================
// 2. SmartContract.java
// ============================================
class SmartContract {
    private String contractId;
    private String code;
    private String deployer;
    private long deployedAt;
    private Map<String, String> methods;
    private boolean validated;
    
    public SmartContract(String contractId, String code, String deployer) {
        this.contractId = contractId;
        this.code = code;
        this.deployer = deployer;
        this.deployedAt = Instant.now().toEpochMilli();
        this.methods = new HashMap<>();
        this.validated = false;
        parseMethods();
    }
    
    private void parseMethods() {
        // Simulation du parsing des méthodes
        methods.put("transfer", "function transfer(address to, uint amount)");
        methods.put("balanceOf", "function balanceOf(address owner) returns (uint)");
        methods.put("approve", "function approve(address spender, uint amount)");
    }
    
    public boolean validate() {
        if (validated) return true;
        
        // Validation du code
        if (code == null || code.isEmpty()) return false;
        if (methods.isEmpty()) return false;
        
        validated = true;
        return true;
    }
    
    // Getters
    public String getContractId() { return contractId; }
    public String getCode() { return code; }
    public String getDeployer() { return deployer; }
    public long getDeployedAt() { return deployedAt; }
    public Map<String, String> getMethods() { return methods; }
    public boolean isValidated() { return validated; }
}
