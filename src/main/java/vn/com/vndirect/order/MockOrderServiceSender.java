package vn.com.vndirect.order;

import org.apache.log4j.Logger;

public class MockOrderServiceSender implements OrderServiceSender {

    private static final Logger logger = Logger.getLogger(MockOrderServiceSender.class);

    @Override
    public String placeOrder(Order order) {
        logger.info("Send place order request: " + order);
        return "12345678";
    }

    @Override
    public void cancelOrder(Order order) {
        logger.info("Send cancel order request: " + order);
    }

    @Override
    public void replaceOrder(Order order) {
        logger.info("Send replace order request: " + order);
    }

}
