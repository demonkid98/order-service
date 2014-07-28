package vn.com.vndirect.order;

public interface OrderServiceSender {
    public String placeOrder(Order order);
    public void cancelOrder(Order order);
    public void replaceOrder(Order order);
}
