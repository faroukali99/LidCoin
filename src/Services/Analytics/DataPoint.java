// ============================================
// 3. DataPoint.java
// ============================================
class DataPoint {
    double value;
    long timestamp;
    
    public DataPoint(double value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }
    
    public double getValue() { return value; }
    public long getTimestamp() { return timestamp; }
}
