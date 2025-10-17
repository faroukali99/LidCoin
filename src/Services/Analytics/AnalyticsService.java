import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. AnalyticsService.java
// ============================================
public class AnalyticsService {
    private Map<String, TransactionMetrics> metrics;
    private Map<String, List<DataPoint>> timeSeriesData;
    private ReportingService reportingService;
    
    public AnalyticsService() {
        this.metrics = new ConcurrentHashMap<>();
        this.timeSeriesData = new ConcurrentHashMap<>();
        this.reportingService = new ReportingService();
    }
    
    public void recordTransaction(String address, String type, double amount) {
        TransactionMetrics metric = metrics.computeIfAbsent(address, k -> new TransactionMetrics());
        metric.recordTransaction(type, amount);
        
        // Enregistrer dans les séries temporelles
        DataPoint point = new DataPoint(amount, Instant.now().toEpochMilli());
        timeSeriesData.computeIfAbsent(address, k -> new ArrayList<>()).add(point);
    }
    
    public TransactionMetrics getMetrics(String address) {
        return metrics.getOrDefault(address, new TransactionMetrics());
    }
    
    public Map<String, Object> getGlobalStatistics() {
        Map<String, Object> stats = new HashMap<>();
        long totalTx = metrics.values().stream().mapToLong(m -> m.totalTransactions).sum();
        double totalVol = metrics.values().stream().mapToDouble(m -> m.totalVolume).sum();
        
        stats.put("totalTransactions", totalTx);
        stats.put("totalVolume", totalVol);
        stats.put("averageTransactionSize", totalTx > 0 ? totalVol / totalTx : 0);
        stats.put("activeAddresses", metrics.size());
        stats.put("timestamp", Instant.now().toEpochMilli());
        
        return stats;
    }
    
    public List<DataPoint> getTimeSeries(String address, long startTime, long endTime) {
        List<DataPoint> allPoints = timeSeriesData.getOrDefault(address, new ArrayList<>());
        return allPoints.stream()
            .filter(p -> p.timestamp >= startTime && p.timestamp <= endTime)
            .toList();
    }
    
    public String generateReport(String address, String reportType) {
        return reportingService.generate(address, reportType, metrics.get(address));
    }
}
