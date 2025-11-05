// ============================================
package com.lidcoin.blockchain;

import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 2. Blockchain.java
// ============================================
class Blockchain {
    private List<Block> chain;
    private int difficulty;
    private List<Transaction> pendingTransactions;
    private double miningReward;
    private Map<String, Double> balances;
    private List<String> validators;
    private static final double TOTAL_SUPPLY = 21000000.0;
    private double circulatingSupply;
    
    public Blockchain() {
        this.chain = new ArrayList<>();
        this.difficulty = 4;
        this.pendingTransactions = new ArrayList<>();
        this.miningReward = 50.0;
        this.balances = new HashMap<>();
        this.validators = new ArrayList<>();
        this.circulatingSupply = 0.0;
        createGenesisBlock();
    }
    
    private void createGenesisBlock() {
        List<Transaction> genesisTransactions = new ArrayList<>();
        Block genesis = new Block(0, genesisTransactions, "0", "GENESIS");
        genesis.mineBlock(difficulty);
        chain.add(genesis);
        System.out.println("Bloc Genesis créé");
    }
    
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }
    
    public void addTransaction(Transaction transaction) {
        if (!isValidTransaction(transaction)) {
            throw new RuntimeException("Transaction invalide");
        }
        pendingTransactions.add(transaction);
        System.out.println("Transaction ajoutée au pool: " + transaction.getTransactionId());
    }
    
    private boolean isValidTransaction(Transaction transaction) {
        if (transaction.getSender() == null || transaction.getReceiver() == null) {
            return false;
        }
        if (transaction.getAmount() <= 0) {
            return false;
        }
        double senderBalance = balances.getOrDefault(transaction.getSender(), 0.0);
        return senderBalance >= (transaction.getAmount() + transaction.getFee());
    }
    
    public void minePendingTransactions(String minerAddress) {
        Block block = new Block(
            chain.size(),
            new ArrayList<>(pendingTransactions),
            getLatestBlock().getHash(),
            minerAddress
        );
        
        block.mineBlock(difficulty);
        chain.add(block);
        
        // Traiter les transactions
        for (Transaction tx : pendingTransactions) {
            processTransaction(tx);
        }
        
        // Récompense du mineur
        if (circulatingSupply + miningReward <= TOTAL_SUPPLY) {
            balances.put(minerAddress, balances.getOrDefault(minerAddress, 0.0) + miningReward);
            circulatingSupply += miningReward;
        }
        
        pendingTransactions.clear();
        System.out.println("Bloc #" + block.getIndex() + " miné par " + minerAddress);
    }
    
    private void processTransaction(Transaction tx) {
        double senderBalance = balances.getOrDefault(tx.getSender(), 0.0);
        balances.put(tx.getSender(), senderBalance - tx.getAmount() - tx.getFee());
        
        double receiverBalance = balances.getOrDefault(tx.getReceiver(), 0.0);
        balances.put(tx.getReceiver(), receiverBalance + tx.getAmount());
    }
    
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);
            
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Hash du bloc #" + i + " invalide");
                return false;
            }
            
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Lien avec bloc précédent invalide #" + i);
                return false;
            }
            
            if (!currentBlock.getMerkleRoot().equals(currentBlock.calculateMerkleRoot())) {
                System.out.println("Merkle root invalide #" + i);
                return false;
            }
        }
        return true;
    }
    
    public double getBalance(String address) {
        return balances.getOrDefault(address, 0.0);
    }
    
    public void addValidator(String validatorAddress) {
        if (!validators.contains(validatorAddress)) {
            validators.add(validatorAddress);
            System.out.println("Validateur ajouté: " + validatorAddress);
        }
    }
    
    public Block getBlock(int index) {
        if (index >= 0 && index < chain.size()) {
            return chain.get(index);
        }
        return null;
    }
    
    public List<Block> getBlocks(int start, int count) {
        int end = Math.min(start + count, chain.size());
        return new ArrayList<>(chain.subList(start, end));
    }
    
    // Getters
    public List<Block> getChain() { return chain; }
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public List<Transaction> getPendingTransactions() { return pendingTransactions; }
    public double getMiningReward() { return miningReward; }
    public double getCirculatingSupply() { return circulatingSupply; }
    public int getBlockHeight() { return chain.size() - 1; }
    public List<String> getValidators() { return validators; }
}
