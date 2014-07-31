package vn.com.vndirect.order;

import java.util.Random;

import org.apache.log4j.Logger;

public class MockOrderServiceSender implements OrderServiceSender {

    private static final Logger logger = Logger.getLogger(MockOrderServiceSender.class);
    private Random random = new Random();

    @Override
    public String placeOrder(Order order) {
        logger.info("Send place order request: " + order);
        return String.format("%09d", random.nextInt(Integer.MAX_VALUE));
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
