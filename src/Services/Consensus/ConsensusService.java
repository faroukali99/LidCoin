import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.security.SecureRandom;

// ============================================
// 1. ConsensusService.java
// ============================================
public class ConsensusService {
    private ConsensusType type;
    private Map<String, Double> stakes;
    private List<String> validators;
    private Random random;
    private ProofOfWork pow;
    private ProofOfStake pos;
    private DelegatedProofOfStake dpos;
    
    public enum ConsensusType {
        PROOF_OF_WORK, PROOF_OF_STAKE, DELEGATED_PROOF_OF_STAKE, HYBRID
    }
    
    public ConsensusService(ConsensusType type) {
        this.type = type;
        this.stakes = new ConcurrentHashMap<>();
        this.validators = new ArrayList<>();
        this.random = new SecureRandom();
        this.pow = new ProofOfWork();
        this.pos = new ProofOfStake(stakes);
        this.dpos = new DelegatedProofOfStake();
    }
    
    public String selectValidator() {
        if (validators.isEmpty()) {
            throw new RuntimeException("Aucun validateur disponible");
        }
        
        switch (type) {
            case PROOF_OF_WORK:
                return pow.selectMiner(validators);
            case PROOF_OF_STAKE:
                return pos.selectValidator(validators);
            case DELEGATED_PROOF_OF_STAKE:
                return dpos.selectDelegate(validators);
            case HYBRID:
                return selectHybridValidator();
            default:
                return validators.get(random.nextInt(validators.size()));
        }
    }
    
    private String selectHybridValidator() {
        // 50% PoW, 50% PoS
        if (random.nextBoolean()) {
            return pow.selectMiner(validators);
        } else {
            return pos.selectValidator(validators);
        }
    }
    
    public void addValidator(String address, double stake) {
        if (!validators.contains(address)) {
            validators.add(address);
            stakes.put(address, stake);
            System.out.println("Validateur ajouté: " + address + " (Stake: " + stake + ")");
        }
    }
    
    public void removeValidator(String address) {
        validators.remove(address);
        stakes.remove(address);
        System.out.println("Validateur retiré: " + address);
    }
    
    public void updateStake(String address, double newStake) {
        if (stakes.containsKey(address)) {
            stakes.put(address, newStake);
            System.out.println("Stake mis à jour pour " + address + ": " + newStake);
        }
    }
    
    public double getStake(String address) {
        return stakes.getOrDefault(address, 0.0);
    }
    
    public double getTotalStake() {
        return stakes.values().stream().mapToDouble(Double::doubleValue).sum();
    }
    
    public List<String> getValidators() {
        return new ArrayList<>(validators);
    }
    
    public int getValidatorCount() {
        return validators.size();
    }
    
    public ConsensusType getType() {
        return type;
    }
    
    public void setType(ConsensusType type) {
        this.type = type;
        System.out.println("Type de consensus changé: " + type);
    }
}
