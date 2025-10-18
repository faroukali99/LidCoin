import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. SmartContractEngine.java
// ============================================
public class SmartContractEngine {
    private Map<String, SmartContract> contracts;
    private Map<String, ContractState> contractStates;
    private VMExecutor vmExecutor;
    private long gasLimit;
    private double gasPrice;
    
    public SmartContractEngine() {
        this.contracts = new ConcurrentHashMap<>();
        this.contractStates = new ConcurrentHashMap<>();
        this.vmExecutor = new VMExecutor();
        this.gasLimit = 1000000;
        this.gasPrice = 0.00001;
    }
    
    public String deployContract(String code, String deployer, Map<String, Object> constructorParams) {
        String contractId = generateContractId(deployer);
        SmartContract contract = new SmartContract(contractId, code, deployer);
        
        contracts.put(contractId, contract);
        contractStates.put(contractId, new ContractState(contractId));
        
        // Exécuter le constructeur
        if (constructorParams != null && !constructorParams.isEmpty()) {
            vmExecutor.executeConstructor(contract, constructorParams);
        }
        
        System.out.println("Smart Contract déployé: " + contractId + " par " + deployer);
        return contractId;
    }
    
    public Object executeContract(String contractId, String method, Map<String, Object> parameters, String caller) {
        SmartContract contract = contracts.get(contractId);
        ContractState state = contractStates.get(contractId);
        
        if (contract == null || state == null || !state.isActive()) {
            throw new RuntimeException("Contract non trouvé ou inactif");
        }
        
        if (!contract.validate()) {
            throw new RuntimeException("Contract invalide");
        }
        
        // Calculer le gas nécessaire
        long gasUsed = calculateGasUsage(method, parameters);
        if (gasUsed > gasLimit) {
            throw new RuntimeException("Gas limit dépassé");
        }
        
        // Exécuter dans la VM
        Object result = vmExecutor.execute(contract, method, parameters, state);
        
        state.setLastExecuted(Instant.now().toEpochMilli());
        state.incrementExecutionCount();
        
        System.out.println("Contract " + contractId + " exécuté: " + method);
        return result;
    }
    
    private String generateContractId(String deployer) {
        return "CT" + UUID.randomUUID().toString().substring(0, 16) + "_" + deployer.substring(0, 8);
    }
    
    private long calculateGasUsage(String method, Map<String, Object> parameters) {
        // Simulation du calcul de gas
        long baseGas = 21000;
        long paramGas = parameters.size() * 1000;
        return baseGas + paramGas;
    }
    
    public ContractState getContractState(String contractId) {
        return contractStates.get(contractId);
    }
    
    public SmartContract getContract(String contractId) {
        return contracts.get(contractId);
    }
    
    public void deactivateContract(String contractId) {
        ContractState state = contractStates.get(contractId);
        if (state != null) {
            state.setActive(false);
        }
    }
}
