import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. APIGateway.java
// ============================================
public class APIGateway {
    private Map<String, ServiceEndpoint> endpoints;
    private RateLimiter rateLimiter;
    private LoadBalancer loadBalancer;
    private Map<String, List<APIRequest>> requestLog;
    private AuthenticationManager authManager;
    
    public APIGateway() {
        this.endpoints = new ConcurrentHashMap<>();
        this.rateLimiter = new RateLimiter(100, 60);
        this.loadBalancer = new LoadBalancer();
        this.requestLog = new ConcurrentHashMap<>();
        this.authManager = new AuthenticationManager();
        registerEndpoints();
    }
    
    private void registerEndpoints() {
        // Wallet endpoints
        registerEndpoint("/api/v1/wallet/create", "WalletService", "POST", true);
        registerEndpoint("/api/v1/wallet/balance/{address}", "WalletService", "GET", true);
        registerEndpoint("/api/v1/wallet/history/{address}", "WalletService", "GET", true);
        
        // Transaction endpoints
        registerEndpoint("/api/v1/transaction/send", "TransactionService", "POST", true);
        registerEndpoint("/api/v1/transaction/{txId}", "TransactionService", "GET", false);
        registerEndpoint("/api/v1/transaction/history/{address}", "TransactionService", "GET", true);
        
        // Blockchain endpoints
        registerEndpoint("/api/v1/blockchain/stats", "BlockchainService", "GET", false);
        registerEndpoint("/api/v1/blockchain/block/{index}", "BlockchainService", "GET", false);
        registerEndpoint("/api/v1/blockchain/latest", "BlockchainService", "GET", false);
        
        // Exchange endpoints
        registerEndpoint("/api/v1/exchange/rate", "ExchangeService", "GET", false);
        registerEndpoint("/api/v1/exchange/order", "ExchangeService", "POST", true);
        registerEndpoint("/api/v1/exchange/orders/{pair}", "ExchangeService", "GET", true);
        
        // KYC endpoints
        registerEndpoint("/api/v1/kyc/submit", "KYCService", "POST", true);
        registerEndpoint("/api/v1/kyc/status/{userId}", "KYCService", "GET", true);
        
        System.out.println("✅ API Gateway: " + endpoints.size() + " endpoints enregistrés");
    }
    
    private void registerEndpoint(String path, String service, String method, boolean requiresAuth) {
        ServiceEndpoint endpoint = new ServiceEndpoint(service, path, method, requiresAuth);
        endpoints.put(path, endpoint);
    }
    
    public APIResponse handleRequest(String path, String method, String token, Map<String, Object> body) {
        long startTime = System.currentTimeMillis();
        
        // Extract userId
        String userId = authManager.extractUserId(token);
        APIRequest request = new APIRequest(path, method, userId);
        
        // Rate limiting
        if (!rateLimiter.allowRequest(userId)) {
            return new APIResponse(429, "Too Many Requests", null);
        }
        
        // Find endpoint
        ServiceEndpoint endpoint = endpoints.get(path);
        if (endpoint == null) {
            return new APIResponse(404, "Endpoint not found", null);
        }
        
        // Authentication
        if (endpoint.requiresAuth() && !authManager.validateToken(token)) {
            return new APIResponse(401, "Unauthorized", null);
        }
        
        // Route to service
        APIResponse response = routeRequest(endpoint, body);
        
        // Log
        request.setResponseCode(response.getStatusCode());
        request.setResponseTime(System.currentTimeMillis() - startTime);
        requestLog.computeIfAbsent(userId, k -> new ArrayList<>()).add(request);
        
        return response;
    }
    
    private APIResponse routeRequest(ServiceEndpoint endpoint, Map<String, Object> body) {
        String serviceUrl = loadBalancer.getServiceUrl(endpoint.getServiceName());
        
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("service", endpoint.getServiceName());
        responseData.put("path", endpoint.getPath());
        responseData.put("timestamp", Instant.now().toEpochMilli());
        
        return new APIResponse(200, "Success", responseData);
    }
    
    public Map<String, Object> getGatewayStats() {
        Map<String, Object> stats = new HashMap<>();
        int totalRequests = requestLog.values().stream().mapToInt(List::size).sum();
        stats.put("totalRequests", totalRequests);
        stats.put("activeEndpoints", endpoints.size());
        stats.put("uniqueUsers", requestLog.size());
        return stats;
    }
}
