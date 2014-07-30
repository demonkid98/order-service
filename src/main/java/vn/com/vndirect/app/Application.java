package vn.com.vndirect.app;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import vn.com.vndirect.rest.webservice.OrderService;
import vn.com.vndirect.zk.OrderServiceApplicationListener;

public class Application extends ResourceConfig {

    public Application() {
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        OrderServiceApplicationListener listener = context.getBean(
                "orderServiceApplicationListener", OrderServiceApplicationListener.class);

        org.zk.Application zkApp = new org.zk.Application();
        zkApp.registListener(listener);
        zkApp.start();

        register(OrderService.class);
    }

}
