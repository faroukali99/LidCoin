// ============================================
// 7. ExchangeServiceTest.java
// ============================================
package com.lidcoin.exchange;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeServiceTest {
    
    private ExchangeService exchangeService;
    
    @BeforeEach
    void setUp() {
        exchangeService = new ExchangeService();
    }
    
    @Test
    @DisplayName("Should place buy order")
    void testPlaceBuyOrder() {
        String orderId = exchangeService.placeOrder("user123", "LDC/USD", ExchangeService.OrderType.BUY, 100.0, 1.0);
        
        assertNotNull(orderId);
        assertTrue(orderId.startsWith("ORD"));
    }
    
    @Test
    @DisplayName("Should place sell order")
    void testPlaceSellOrder() {
        String orderId = exchangeService.placeOrder("user123", "LDC/USD", ExchangeService.OrderType.SELL, 100.0, 1.0);
        
        assertNotNull(orderId);
    }
    
    @Test
    @DisplayName("Should get exchange rate")
    void testGetExchangeRate() {
        ExchangePair pair = exchangeService.getExchangeRate("LDC/USD");
        
        assertNotNull(pair);
        assertEquals("LDC", pair.getBaseCurrency());
        assertEquals("USD", pair.getQuoteCurrency());
    }
    
    @Test
    @DisplayName("Should reject invalid pair")
    void testInvalidPair() {
        assertThrows(RuntimeException.class, () -> {
            exchangeService.placeOrder("user123", "INVALID/PAIR", ExchangeService.OrderType.BUY, 100.0, 1.0);
        });
    }
}
