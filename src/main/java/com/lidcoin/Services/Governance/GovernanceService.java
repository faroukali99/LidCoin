// ============================================
// SERVICE: GOVERNANCE (DAO)
// Location: src/main/java/com/lidcoin/governance/
// ============================================

package com.lidcoin.governance;

import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. GovernanceService.java
// ============================================
public class GovernanceService {
    private Map<String, Proposal> proposals;
    private Map<String, Vote> votes;
    private VotingPower votingPowerCalculator;
    private ProposalExecutor executor;
    private static final long VOTING_PERIOD = 604800000; // 7 jours
    private static final double QUORUM = 0.1; // 10% des tokens
    
    public GovernanceService() {
        this.proposals = new ConcurrentHashMap<>();
        this.votes = new ConcurrentHashMap<>();
        this.votingPowerCalculator = new VotingPower();
        this.executor = new ProposalExecutor();
    }
    
    public Proposal createProposal(String creator, String title, String description, ProposalType type) {
        // Vérifier que le créateur a assez de tokens stakés
        double votingPower = votingPowerCalculator.calculatePower(creator);
        if (votingPower < 1000) {
            throw new RuntimeException("Minimum 1000 tokens stakés requis pour créer une proposition");
        }
        
        Proposal proposal = new Proposal(creator, title, description, type, VOTING_PERIOD);
        proposals.put(proposal.getProposalId(), proposal);
        
        System.out.println("📝 Proposition créée: " + title);
        return proposal;
    }
    
    public void vote(String userId, String proposalId, boolean support) {
        Proposal proposal = proposals.get(proposalId);
        if (proposal == null) {
            throw new RuntimeException("Proposition non trouvée");
        }
        
        if (proposal.getStatus() != ProposalStatus.ACTIVE) {
            throw new RuntimeException("Vote fermé pour cette proposition");
        }
        
        double votingPower = votingPowerCalculator.calculatePower(userId);
        if (votingPower == 0) {
            throw new RuntimeException("Aucun pouvoir de vote");
        }
        
        String voteId = proposalId + "_" + userId;
        Vote vote = new Vote(userId, proposalId, support, votingPower);
        votes.put(voteId, vote);
        
        proposal.addVote(support, votingPower);
        
        System.out.println("🗳️ Vote enregistré: " + (support ? "POUR" : "CONTRE") + " avec pouvoir " + votingPower);
    }
    
    public void closeProposal(String proposalId) {
        Proposal proposal = proposals.get(proposalId);
        if (proposal == null) return;
        
        if (!proposal.hasEnded()) {
            throw new RuntimeException("Période de vote non terminée");
        }
        
        // Vérifier le quorum
        double totalSupply = 21000000.0;
        double totalVoted = proposal.getVotesFor() + proposal.getVotesAgainst();
        
        if (totalVoted < totalSupply * QUORUM) {
            proposal.setStatus(ProposalStatus.FAILED);
            System.out.println("❌ Proposition échouée: Quorum non atteint");
            return;
        }
        
        // Vérifier le résultat
        if (proposal.getVotesFor() > proposal.getVotesAgainst()) {
            proposal.setStatus(ProposalStatus.PASSED);
            System.out.println("✅ Proposition acceptée");
            
            // Exécuter la proposition
            executor.execute(proposal);
        } else {
            proposal.setStatus(ProposalStatus.FAILED);
            System.out.println("❌ Proposition rejetée");
        }
    }
    
    public Proposal getProposal(String proposalId) {
        return proposals.get(proposalId);
    }
    
    public List<Proposal> getAllProposals() {
        return new ArrayList<>(proposals.values());
    }
    
    public List<Proposal> getActiveProposals() {
        return proposals.values().stream()
            .filter(p -> p.getStatus() == ProposalStatus.ACTIVE)
            .toList();
    }
}
