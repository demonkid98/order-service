package vn.com.vndirect.app;

import org.glassfish.jersey.server.ResourceConfig;

import vn.com.vndirect.webservice.OrderService;

public class Application extends ResourceConfig {

    public Application() {
//        org.zk.Application zkApp = new org.zk.Application();
//        zkApp.registListener(new OrderSerivceApplicationListener());
//        zkApp.start();

        register(new ApplicationBinder());
        register(OrderService.class);
    }

}
