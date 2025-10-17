import java.util.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// ============================================
// 1. ExchangeService.java
// ============================================
public class ExchangeService {
    private Map<String, ExchangePair> exchangePairs;
    private OrderBook orderBook;
    private TradingEngine tradingEngine;
    private Map<String, List<Trade>> tradeHistory;
    
    public ExchangeService() {
        this.exchangePairs = new ConcurrentHashMap<>();
        this.orderBook = new OrderBook();
        this.tradingEngine = new TradingEngine(orderBook);
        this.tradeHistory = new ConcurrentHashMap<>();
        initializeExchangePairs();
    }
    
    private void initializeExchangePairs() {
        exchangePairs.put("LDC/USD", new ExchangePair("LDC", "USD", 1.0));
        exchangePairs.put("LDC/EUR", new ExchangePair("LDC", "EUR", 0.85));
        exchangePairs.put("LDC/XOF", new ExchangePair("LDC", "XOF", 655.0));
        exchangePairs.put("LDC/BTC", new ExchangePair("LDC", "BTC", 0.000025));
    }
    
    public String placeOrder(String userId, String pair, OrderType type, double amount, double price) {
        if (!exchangePairs.containsKey(pair)) {
            throw new RuntimeException("Paire d'échange invalide: " + pair);
        }
        
        ExchangeOrder order = new ExchangeOrder(userId, pair, type, amount, price);
        orderBook.addOrder(order);
        
        // Tenter de matcher l'ordre
        List<Trade> trades = tradingEngine.matchOrder(order);
        if (!trades.isEmpty()) {
            tradeHistory.computeIfAbsent(pair, k -> new ArrayList<>()).addAll(trades);
            updatePairPrice(pair, trades.get(trades.size() - 1).getPrice());
        }
        
        System.out.println("Ordre placé: " + order.getOrderId() + " (" + type + " " + amount + " " + pair + " @ " + price + ")");
        return order.getOrderId();
    }
    
    private void updatePairPrice(String pair, double newPrice) {
        ExchangePair exchangePair = exchangePairs.get(pair);
        if (exchangePair != null) {
            exchangePair.setLastPrice(newPrice);
            exchangePair.updateStats(newPrice);
        }
    }
    
    public boolean cancelOrder(String orderId) {
        return orderBook.cancelOrder(orderId);
    }
    
    public ExchangePair getExchangeRate(String pair) {
        return exchangePairs.get(pair);
    }
    
    public List<ExchangeOrder> getOrderBook(String pair, OrderType type) {
        return orderBook.getOrders(pair, type);
    }
    
    public List<Trade> getTradeHistory(String pair, int limit) {
        List<Trade> trades = tradeHistory.getOrDefault(pair, new ArrayList<>());
        return trades.subList(Math.max(0, trades.size() - limit), trades.size());
    }
    
    public Map<String, ExchangePair> getAllPairs() {
        return new HashMap<>(exchangePairs);
    }
    
    public enum OrderType {
        BUY, SELL
    }
}
