package vn.com.vndirect.webservice;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import vn.com.vndirect.error.OrderValidator;
import vn.com.vndirect.error.Validator;
import vn.com.vndirect.order.Order;
import vn.com.vndirect.order.OrderServiceSender;
import vn.com.vndirect.stock.StockInfoService;

@Path("/order")
public class OrderService {

    @Inject
    private OrderServiceSender serviceSender;

    @Inject
    private StockInfoService stockService;

    private boolean isActive = true;

    @POST
    @Path("/placeOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public String placeOrder(@FormParam("account") String account, @FormParam("symbol") String symbol,
            @FormParam("price") float price, @FormParam("volume") int volume, @FormParam("type") String type) {
        Order order = new Order(account, symbol, price, volume, type);
        Validator validator = new OrderValidator(order, stockService);

        if (!validator.validate()) {
            throw (new NotAcceptableException(validator.getFirstError()));
        }
        return serviceSender.placeOrder(order);
    }

    @POST
    @Path("/replaceOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public String replaceOrder(@FormParam("orderId") String orderId, @FormParam("account") String account,
            @FormParam("symbol") String symbol, @FormParam("price") float price, @FormParam("volume") int volume,
            @FormParam("type") String type) {
        Order order = new Order(orderId, account, symbol, price, volume, type);
        Validator validator = new OrderValidator(order, stockService);

        if (!validator.validate()) {
            throw (new NotAcceptableException(validator.getFirstError()));
        }
        serviceSender.replaceOrder(order);
        return order.getId();
    }

    @POST
    @Path("/cancelOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public String cancelOrder(@FormParam("orderId") String orderId, @FormParam("account") String account,
            @FormParam("symbol") String symbol, @FormParam("price") float price, @FormParam("volume") int volume,
            @FormParam("type") String type) {
        Order order = new Order(orderId, account, symbol, price, volume, type);
        serviceSender.cancelOrder(order);
        return order.getId();
    }

    public void setServiceSender(OrderServiceSender serviceSender) {
        this.serviceSender = serviceSender;
    }

    public void setStockService(StockInfoService stockService) {
        this.stockService = stockService;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
