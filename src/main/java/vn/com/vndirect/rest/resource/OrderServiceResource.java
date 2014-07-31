package vn.com.vndirect.rest.resource;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import vn.com.vndirect.error.OrderValidator;
import vn.com.vndirect.error.Validator;
import vn.com.vndirect.order.Order;
import vn.com.vndirect.order.OrderServiceSender;
import vn.com.vndirect.stock.StockInfoService;

public class OrderServiceResource extends ServiceResource {

    @Autowired
    private OrderServiceSender serviceSender;

    @Autowired
    private StockInfoService stockService;

    public Order placeOrder(String account, String symbol, float price, int volume, String type)
            throws NotFoundException, NotAcceptableException {
        if (! getAppState().isActive()) {
            throw new NotFoundException();
        }

        Order order = new Order(account, symbol, price, volume, type);
        Validator validator = new OrderValidator(order, stockService);

        if (!validator.validate()) {
            throw new NotAcceptableException(validator.getFirstError());
        }
        String orderId = serviceSender.placeOrder(order);
        order.setId(orderId);
        return order;
    }

    public String replaceOrder(String orderId, String account, String symbol, float price, int volume, String type)
            throws NotFoundException, NotAcceptableException {
        if (! getAppState().isActive()) {
            throw new NotFoundException();
        }

        Order order = new Order(orderId, account, symbol, price, volume, type);
        Validator validator = new OrderValidator(order, stockService);

        if (!validator.validate()) {
            throw new NotAcceptableException(validator.getFirstError());
        }
        serviceSender.replaceOrder(order);
        return orderId;
    }

    public String cancelOrder(String orderId, String account, String symbol, float price, int volume, String type)
            throws NotFoundException {
        if (! getAppState().isActive()) {
            throw new NotFoundException();
        }

        Order order = new Order(orderId, account, symbol, price, volume, type);
        serviceSender.cancelOrder(order);
        return orderId;
    }

    public void setServiceSender(OrderServiceSender serviceSender) {
        this.serviceSender = serviceSender;
    }

    public void setStockService(StockInfoService stockService) {
        this.stockService = stockService;
    }

}
