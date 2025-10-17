// ============================================
// 4. OrderBook.java
// ============================================
class OrderBook {
    private Map<String, PriorityQueue<ExchangeOrder>> buyOrders;
    private Map<String, PriorityQueue<ExchangeOrder>> sellOrders;
    private Map<String, ExchangeOrder> allOrders;
    
    public OrderBook() {
        this.buyOrders = new ConcurrentHashMap<>();
        this.sellOrders = new ConcurrentHashMap<>();
        this.allOrders = new ConcurrentHashMap<>();
    }
    
    public void addOrder(ExchangeOrder order) {
        allOrders.put(order.getOrderId(), order);
        
        if (order.getType() == ExchangeService.OrderType.BUY) {
            buyOrders.computeIfAbsent(order.getPair(), k -> new PriorityQueue<>()).add(order);
        } else {
            sellOrders.computeIfAbsent(order.getPair(), k -> new PriorityQueue<>()).add(order);
        }
    }
    
    public boolean cancelOrder(String orderId) {
        ExchangeOrder order = allOrders.get(orderId);
        if (order != null && order.getStatus() == ExchangeOrder.OrderStatus.OPEN) {
            order.setStatus(ExchangeOrder.OrderStatus.CANCELLED);
            removeOrder(order);
            return true;
        }
        return false;
    }
    
    private void removeOrder(ExchangeOrder order) {
        if (order.getType() == ExchangeService.OrderType.BUY) {
            PriorityQueue<ExchangeOrder> queue = buyOrders.get(order.getPair());
            if (queue != null) queue.remove(order);
        } else {
            PriorityQueue<ExchangeOrder> queue = sellOrders.get(order.getPair());
            if (queue != null) queue.remove(order);
        }
    }
    
    public List<ExchangeOrder> getOrders(String pair, ExchangeService.OrderType type) {
        PriorityQueue<ExchangeOrder> queue = type == ExchangeService.OrderType.BUY ? 
            buyOrders.get(pair) : sellOrders.get(pair);
        return queue != null ? new ArrayList<>(queue) : new ArrayList<>();
    }
    
    public ExchangeOrder getBestBuyOrder(String pair) {
        PriorityQueue<ExchangeOrder> queue = buyOrders.get(pair);
        return queue != null && !queue.isEmpty() ? queue.peek() : null;
    }
    
    public ExchangeOrder getBestSellOrder(String pair) {
        PriorityQueue<ExchangeOrder> queue = sellOrders.get(pair);
        return queue != null && !queue.isEmpty() ? queue.peek() : null;
    }
}
