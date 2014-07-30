package vn.com.vndirect.rest.resource;

import static org.junit.Assert.*;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vn.com.vndirect.error.OrderValidator;
import vn.com.vndirect.order.Order;
import vn.com.vndirect.rest.resource.OrderServiceResource;

public class OrderServiceResourceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-test/spring-bean.xml");
    private OrderServiceResource resource;
    private OrderServiceResource inactiveResource;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
//        orderService = context.getBean("orderServiceProxy", OrderService.class);
        resource = context.getBean("orderServiceResource", OrderServiceResource.class);
        inactiveResource = context.getBean("inactiveOrderServiceResource", OrderServiceResource.class);
    }

    @Test
    public void testInit() {
        assertNotNull(resource);
    }

    @Test
    public void testPlaceOrderShouldReturnOrderIdIfValid() {
        Order order = resource.placeOrder("10000", "VND", 12.5F, 100, "ATC");
        assertEquals("12345678", order.getId());
    }

    @Test
    public void testPlaceOrderShouldThrow406IfInvalid() {
        thrown.expect(NotAcceptableException.class);
        thrown.expectMessage(OrderValidator.ORDER_TYPE_INVALID_MSG);
        resource.placeOrder("10000", "VND", 12.5F, 100, "AAA");
    }

    @Test
    public void testPlaceOrderShouldThrow404IfBeingSlave() {
        thrown.expect(NotFoundException.class);
        inactiveResource.placeOrder("10000", "VND", 12.5F, 100, "ATC");
    }

    @Test
    public void testCancelOrderShouldReturnOrderId() {
        String orderId = resource.cancelOrder("12345678", "10000", "VND", 12.5F, 100, "ATC");
        assertEquals("12345678", orderId);
    }

    @Test
    public void testCancelOrderShouldThrow404IfBeingSlave() {
        thrown.expect(NotFoundException.class);
        inactiveResource.cancelOrder("12345678", "10000", "VND", 12.5F, 100, "ATC");
    }

    @Test
    public void testReplaceOrderShouldReturnOrderId() {
        String orderId = resource.replaceOrder("12345678", "10000", "VND", 12.5F, 100, "ATC");
        assertEquals("12345678", orderId);
    }

    @Test
    public void testReplaceOrderShouldThrow406IfInvalid() {
        thrown.expect(NotAcceptableException.class);
        thrown.expectMessage(OrderValidator.PRICE_INVALID_MSG);
        resource.placeOrder("10000", "VND", 1000, 100, "ATC");
    }

    @Test
    public void testReplaceOrderShouldThrow404IfBeingSlave() {
        thrown.expect(NotFoundException.class);
        inactiveResource.replaceOrder("12345678", "10000", "VND", 15, 100, "ATC");
    }

}
