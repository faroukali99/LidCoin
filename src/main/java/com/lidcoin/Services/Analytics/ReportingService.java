// ============================================
// 4. ReportingService.java
// ============================================
class ReportingService {
    
    public String generate(String address, String reportType, TransactionMetrics metrics) {
        if (metrics == null) {
            return "Aucune donnée disponible";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("=== RAPPORT ").append(reportType.toUpperCase()).append(" ===\n");
        report.append("Adresse: ").append(address).append("\n");
        report.append("Total Transactions: ").append(metrics.totalTransactions).append("\n");
        report.append("Volume Total: ").append(String.format("%.2f", metrics.totalVolume)).append(" LDC\n");
        report.append("Transaction Moyenne: ").append(String.format("%.2f", metrics.averageTransactionSize)).append(" LDC\n");
        report.append("Transaction Max: ").append(String.format("%.2f", metrics.maxTransaction)).append(" LDC\n");
        report.append("Transaction Min: ").append(String.format("%.2f", metrics.minTransaction)).append(" LDC\n");
        report.append("\nRépartition par Type:\n");
        
        metrics.transactionsByType.forEach((type, count) -> 
            report.append("  - ").append(type).append(": ").append(count).append("\n")
        );
        
        return report.toString();
    }
}
