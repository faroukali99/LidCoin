// ============================================
// 4. VMExecutor.java
// ============================================
class VMExecutor {
    private Stack<Object> stack;
    private Map<String, Object> memory;
    
    public VMExecutor() {
        this.stack = new Stack<>();
        this.memory = new HashMap<>();
    }
    
    public void executeConstructor(SmartContract contract, Map<String, Object> params) {
        System.out.println("Exécution du constructeur pour " + contract.getContractId());
        // Initialiser les variables d'état
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            memory.put(entry.getKey(), entry.getValue());
        }
    }
    
    public Object execute(SmartContract contract, String method, 
                         Map<String, Object> parameters, ContractState state) {
        
        System.out.println("Exécution de " + method + " sur " + contract.getContractId());
        
        // Charger les paramètres
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            stack.push(entry.getValue());
        }
        
        // Simuler l'exécution
        Object result = executeMethod(method, state);
        
        return result;
    }
    
    private Object executeMethod(String method, ContractState state) {
        switch (method) {
            case "transfer":
                return handleTransfer(state);
            case "balanceOf":
                return handleBalanceOf(state);
            case "approve":
                return handleApprove(state);
            default:
                throw new RuntimeException("Méthode inconnue: " + method);
        }
    }
    
    private boolean handleTransfer(ContractState state) {
        // Logique de transfert
        state.emitEvent("Transfer");
        return true;
    }
    
    private Object handleBalanceOf(ContractState state) {
        // Retourner le solde
        return state.getState("balance");
    }
    
    private boolean handleApprove(ContractState state) {
        // Logique d'approbation
        state.emitEvent("Approval");
        return true;
    }
}
