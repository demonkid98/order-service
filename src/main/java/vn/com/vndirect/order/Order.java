package vn.com.vndirect.order;

public class Order {

    private String id;
    private String account;
    private String symbol;
    private float price;
    private int quantity;
    private String orderType;

    public Order() {
    }

    public Order(String id, String account, String symbol, float price, int quantity, String orderType) {
        this.id = id;
        this.account = account;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    public Order(String account, String symbol, float price, int quantity, String orderType) {
        this("", account, symbol, price, quantity, orderType);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String toString() {
        return String.format("Order[id=%s, account=%s, symbol=%s, price=%2f, quantity=%d, type=%s]",
                id, account, symbol, price, quantity, orderType);
    }

    public double getValue() {
        return price * quantity;
    }
}
