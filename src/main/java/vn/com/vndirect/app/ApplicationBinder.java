package vn.com.vndirect.app;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import vn.com.vndirect.order.MockOrderServiceSender;
import vn.com.vndirect.order.OrderServiceSender;
import vn.com.vndirect.stock.MockStockInfoService;
import vn.com.vndirect.stock.StockInfoService;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(MockStockInfoService.class).to(StockInfoService.class);
        bind(MockOrderServiceSender.class).to(OrderServiceSender.class);
    }

}
