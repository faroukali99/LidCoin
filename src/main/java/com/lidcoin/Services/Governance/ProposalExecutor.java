// ============================================
// 5. ProposalExecutor.java
// ============================================
class ProposalExecutor {
    
    public void execute(Proposal proposal) {
        System.out.println("⚙️ Exécution de la proposition: " + proposal.getTitle());
        
        switch (proposal.getType()) {
            case PARAMETER_CHANGE:
                executeParameterChange(proposal);
                break;
            case TREASURY_SPEND:
                executeTreasurySpend(proposal);
                break;
            case UPGRADE:
                executeUpgrade(proposal);
                break;
            case GENERAL:
                System.out.println("Proposition générale - Pas d'exécution automatique");
                break;
        }
    }
    
    private void executeParameterChange(Proposal proposal) {
        System.out.println("🔧 Changement de paramètre exécuté");
    }
    
    private void executeTreasurySpend(Proposal proposal) {
        System.out.println("💰 Dépense de la trésorerie exécutée");
    }
    
    private void executeUpgrade(Proposal proposal) {
        System.out.println("🆙 Mise à niveau du système exécutée");
    }
}
