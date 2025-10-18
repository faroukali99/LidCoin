// ============================================
// 6. LoadBalancer.java
// ============================================
class LoadBalancer {
    private Map<String, List<ServiceInstance>> serviceInstances;
    private Map<String, Integer> roundRobinIndex;
    
    private static class ServiceInstance {
        String serviceId;
        String host;
        int port;
        boolean healthy;
        
        ServiceInstance(String serviceId, String host, int port) {
            this.serviceId = serviceId;
            this.host = host;
            this.port = port;
            this.healthy = true;
        }
        
        String getUrl() {
            return "http://" + host + ":" + port;
        }
    }
    
    public LoadBalancer() {
        this.serviceInstances = new ConcurrentHashMap<>();
        this.roundRobinIndex = new ConcurrentHashMap<>();
        initializeServices();
    }
    
    private void initializeServices() {
        registerService("WalletService", "localhost", 8081);
        registerService("TransactionService", "localhost", 8082);
        registerService("BlockchainService", "localhost", 8083);
        registerService("ExchangeService", "localhost", 8084);
        registerService("KYCService", "localhost", 8085);
    }
    
    public void registerService(String serviceName, String host, int port) {
        ServiceInstance instance = new ServiceInstance(serviceName, host, port);
        serviceInstances.computeIfAbsent(serviceName, k -> new ArrayList<>()).add(instance);
    }
    
    public String getServiceUrl(String serviceName) {
        List<ServiceInstance> instances = serviceInstances.get(serviceName);
        if (instances == null || instances.isEmpty()) {
            throw new RuntimeException("Service non disponible: " + serviceName);
        }
        
        int index = roundRobinIndex.getOrDefault(serviceName, 0);
        ServiceInstance instance = instances.get(index % instances.size());
        roundRobinIndex.put(serviceName, (index + 1) % instances.size());
        
        return instance.getUrl();
    }
}
