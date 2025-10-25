// ============================================
// 8. SchedulerConfig.java - Configuration Scheduler
// ============================================
@Configuration
@EnableScheduling
public class SchedulerConfig {
    
    @Autowired
    private StateSynchronizationService syncService;
    
    @Autowired
    private BlockchainService blockchainService;
    
    @Scheduled(fixedRate = 30000) // Toutes les 30 secondes
    public void synchronizeNodes() {
        syncService.synchronizeStates();
    }
    
    @Scheduled(fixedRate = 60000) // Toutes les minutes
    public void adjustDifficulty() {
        blockchainService.adjustDifficulty();
    }
    
    @Scheduled(cron = "0 0 * * * *") // Toutes les heures
    public void cleanupOldData() {
        // Nettoyage des données anciennes
    }
}
