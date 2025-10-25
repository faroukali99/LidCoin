// ============================================
// 4. CacheConfig.java - Configuration Cache
// ============================================
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
            new ConcurrentMapCache("wallets"),
            new ConcurrentMapCache("transactions"),
            new ConcurrentMapCache("blocks"),
            new ConcurrentMapCache("users"),
            new ConcurrentMapCache("exchangeRates")
        ));
        return cacheManager;
    }
}
