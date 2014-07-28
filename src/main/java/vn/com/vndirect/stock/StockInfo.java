package vn.com.vndirect.stock;

public class StockInfo {

    private String symbol;
    private float ceilingPrice;
    private float floorPrice;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(float ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public float getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(float floorPrice) {
        this.floorPrice = floorPrice;
    }

}
