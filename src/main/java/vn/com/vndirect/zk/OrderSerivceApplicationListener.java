package vn.com.vndirect.zk;

import org.zk.ApplicationListener;
import org.zk.ApplicationState;

import vn.com.vndirect.webservice.OrderService;

public class OrderSerivceApplicationListener implements ApplicationListener {

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void onChange(ApplicationState state) {
        orderService.setActive(state == ApplicationState.MASTER);
    }

}
