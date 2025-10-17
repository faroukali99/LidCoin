// ============================================
// 2. ServiceEndpoint.java
// ============================================
class ServiceEndpoint {
    private String serviceName;
    private String path;
    private String method;
    private boolean requiresAuth;
    private List<String> allowedRoles;
    
    public ServiceEndpoint(String serviceName, String path, String method, boolean requiresAuth) {
        this.serviceName = serviceName;
        this.path = path;
        this.method = method;
        this.requiresAuth = requiresAuth;
        this.allowedRoles = new ArrayList<>();
    }
    
    public String getServiceName() { return serviceName; }
    public String getPath() { return path; }
    public String getMethod() { return method; }
    public boolean requiresAuth() { return requiresAuth; }
    public List<String> getAllowedRoles() { return allowedRoles; }
}
