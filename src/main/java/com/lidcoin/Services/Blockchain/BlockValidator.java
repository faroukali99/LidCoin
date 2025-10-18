// ============================================
// 5. BlockValidator.java
// ============================================
class BlockValidator {
    
    public boolean validateBlock(Block block, Block previousBlock) {
        return validateHash(block)
            && validatePreviousHash(block, previousBlock)
            && validateMerkleRoot(block)
            && validateTimestamp(block, previousBlock)
            && validateTransactions(block);
    }
    
    private boolean validateHash(Block block) {
        String calculatedHash = block.calculateHash();
        if (!calculatedHash.equals(block.getHash())) {
            System.out.println("Hash invalide pour le bloc #" + block.getIndex());
            return false;
        }
        return true;
    }
    
    private boolean validatePreviousHash(Block block, Block previousBlock) {
        if (previousBlock == null) return true;
        
        if (!block.getPreviousHash().equals(previousBlock.getHash())) {
            System.out.println("Previous hash invalide pour le bloc #" + block.getIndex());
            return false;
        }
        return true;
    }
    
    private boolean validateMerkleRoot(Block block) {
        String calculatedMerkle = block.calculateMerkleRoot();
        if (!calculatedMerkle.equals(block.getMerkleRoot())) {
            System.out.println("Merkle root invalide pour le bloc #" + block.getIndex());
            return false;
        }
        return true;
    }
    
    private boolean validateTimestamp(Block block, Block previousBlock) {
        if (previousBlock == null) return true;
        
        if (block.getTimestamp() <= previousBlock.getTimestamp()) {
            System.out.println("Timestamp invalide pour le bloc #" + block.getIndex());
            return false;
        }
        
        long now = Instant.now().toEpochMilli();
        if (block.getTimestamp() > now + 7200000) { // 2 heures dans le futur
            System.out.println("Timestamp trop loin dans le futur pour le bloc #" + block.getIndex());
            return false;
        }
        
        return true;
    }
    
    private boolean validateTransactions(Block block) {
        for (Transaction tx : block.getTransactions()) {
            if (tx.getTransactionId() == null || tx.getTransactionId().isEmpty()) {
                System.out.println("Transaction invalide dans le bloc #" + block.getIndex());
                return false;
            }
        }
        return true;
    }
    
    public boolean validateProofOfWork(Block block, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return block.getHash().substring(0, difficulty).equals(target);
    }
}
