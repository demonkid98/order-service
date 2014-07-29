package vn.com.vndirect.webservice;

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

public class OrderServiceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-test/bean.xml");
    private OrderService orderService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        orderService = context.getBean("orderServiceProxy", OrderService.class);
    }

    @Test
    public void testInit() {
        assertNotNull(orderService);
    }

    @Test
    public void testPlaceOrderShouldReturnOrderIdIfValid() {
        assertEquals("12345678", orderService.placeOrder("10000", "VND", 12.5F, 100, "ATC"));
    }

    @Test
    public void testPlaceOrderShouldThrow406IfInvalid() {
        thrown.expect(NotAcceptableException.class);
        thrown.expectMessage(OrderValidator.ORDER_TYPE_INVALID_MSG);
        orderService.placeOrder("10000", "VND", 12.5F, 100, "AAA");
    }

    @Test
    public void testPlaceOrderShouldThrow404IfBeingSlave() {
        orderService.setActive(false);
        thrown.expect(NotFoundException.class);
        orderService.placeOrder("10000", "VND", 12.5F, 100, "ATC");
    }

    @Test
    public void testCancelOrderShouldReturnOrderId() {
        assertEquals("12345678", orderService.cancelOrder("12345678", "10000", "VND", 12.5F, 100, "ATC"));
    }

    @Test
    public void testCancelOrderShouldThrow404IfBeingSlave() {
        orderService.setActive(false);
        thrown.expect(NotFoundException.class);
        orderService.cancelOrder("12345678", "10000", "VND", 12.5F, 100, "ATC");
    }

    @Test
    public void testReplaceOrderShouldReturnOrderId() {
        assertEquals("12345678", orderService.replaceOrder("12345678", "10000", "VND", 12.5F, 100, "ATC"));
    }

    @Test
    public void testReplaceOrderShouldThrow406IfInvalid() {
        thrown.expect(NotAcceptableException.class);
        thrown.expectMessage(OrderValidator.PRICE_INVALID_MSG);
        orderService.replaceOrder("12345678", "10000", "VND", 1000, 100, "ATC");
    }

    @Test
    public void testReplaceOrderShouldThrow404IfBeingSlave() {
        orderService.setActive(false);
        thrown.expect(NotFoundException.class);
        orderService.replaceOrder("12345678", "10000", "VND", 15, 100, "ATC");
    }

}
