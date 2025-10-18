// ============================================
// 5. TradingEngine.java
// ============================================
class TradingEngine {
    private OrderBook orderBook;
    private double tradingFee = 0.001; // 0.1%
    
    public TradingEngine(OrderBook orderBook) {
        this.orderBook = orderBook;
    }
    
    public List<Trade> matchOrder(ExchangeOrder newOrder) {
        List<Trade> trades = new ArrayList<>();
        
        if (newOrder.getType() == ExchangeService.OrderType.BUY) {
            trades.addAll(matchBuyOrder(newOrder));
        } else {
            trades.addAll(matchSellOrder(newOrder));
        }
        
        return trades;
    }
    
    private List<Trade> matchBuyOrder(ExchangeOrder buyOrder) {
        List<Trade> trades = new ArrayList<>();
        
        while (buyOrder.getRemainingAmount() > 0) {
            ExchangeOrder bestSell = orderBook.getBestSellOrder(buyOrder.getPair());
            
            if (bestSell == null || bestSell.getPrice() > buyOrder.getPrice()) {
                break; // Pas de match possible
            }
            
            double tradeAmount = Math.min(buyOrder.getRemainingAmount(), bestSell.getRemainingAmount());
            double tradePrice = bestSell.getPrice();
            
            Trade trade = new Trade(
                buyOrder.getUserId(),
                bestSell.getUserId(),
                buyOrder.getPair(),
                tradeAmount,
                tradePrice,
                tradingFee
            );
            
            buyOrder.fill(tradeAmount);
            bestSell.fill(tradeAmount);
            
            trades.add(trade);
            
            if (bestSell.isFilled()) {
                orderBook.cancelOrder(bestSell.getOrderId());
            }
        }
        
        return trades;
    }
    
    private List<Trade> matchSellOrder(ExchangeOrder sellOrder) {
        List<Trade> trades = new ArrayList<>();
        
        while (sellOrder.getRemainingAmount() > 0) {
            ExchangeOrder bestBuy = orderBook.getBestBuyOrder(sellOrder.getPair());
            
            if (bestBuy == null || bestBuy.getPrice() < sellOrder.getPrice()) {
                break; // Pas de match possible
            }
            
            double tradeAmount = Math.min(sellOrder.getRemainingAmount(), bestBuy.getRemainingAmount());
            double tradePrice = bestBuy.getPrice();
            
            Trade trade = new Trade(
                bestBuy.getUserId(),
                sellOrder.getUserId(),
                sellOrder.getPair(),
                tradeAmount,
                tradePrice,
                tradingFee
            );
            
            sellOrder.fill(tradeAmount);
            bestBuy.fill(tradeAmount);
            
            trades.add(trade);
            
            if (bestBuy.isFilled()) {
                orderBook.cancelOrder(bestBuy.getOrderId());
            }
        }
        
        return trades;
    }
}
