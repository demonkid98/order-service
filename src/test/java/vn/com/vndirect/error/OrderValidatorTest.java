package vn.com.vndirect.error;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import vn.com.vndirect.error.OrderValidator;
import vn.com.vndirect.order.Order;
import vn.com.vndirect.stock.MockStockInfoService;

public class OrderValidatorTest {

    private Order order;
    private OrderValidator validator;

    @Before
    public void setUp() {
        order = new Order();
        validator = new OrderValidator(order);
        validator.setStockService(new MockStockInfoService());
    }

    @Test
    public void testValidateOrderAccountShouldPassIfNotNull() {
        order.setAccount("123456");
        assertTrue(validator.validateAccount());
    }

    @Test
    public void testValidateOrderAccountShouldFailIfNull() {
        order.setAccount(null);
        assertFalse(validator.validateAccount());
        assertEquals(OrderValidator.ACCOUNT_EMPTY_MSG, validator.getError("account").get(0));
    }

    @Test
    public void testValidateOrderAccountShouldFailIfEmpty() {
        order.setAccount("");
        assertFalse(validator.validateAccount());
        assertEquals(OrderValidator.ACCOUNT_EMPTY_MSG, validator.getError("account").get(0));
    }

    @Test
    public void testValidateOrderSymbolShouldPassIfNotNullAndExisted() {
        order.setSymbol("VND");
        assertTrue(validator.validateSymbol());
    }

    @Test
    public void testValidateOrderSymbolShouldFailIfNull() {
        order.setSymbol(null);
        assertFalse(validator.validateSymbol());
        assertEquals(OrderValidator.SYMBOL_EMPTY_MSG, validator.getError("symbol").get(0));
    }

    @Test
    public void testValidateOrderSymbolShouldFailIfEmpty() {
        order.setSymbol("");
        assertFalse(validator.validateSymbol());
        assertEquals(OrderValidator.SYMBOL_EMPTY_MSG, validator.getError("symbol").get(0));
    }

    @Test
    public void testValidateOrderSymbolShouldFailIfNotExisted() {
        order.setSymbol("ABC");
        assertFalse(validator.validateSymbol());
        assertEquals(OrderValidator.SYMBOL_NOT_EXISTED_MSG, validator.getError("symbol").get(0));
    }

    @Test
    public void testValidateOrderPriceShouldPassIfPositiveAndValid() {
        order.setSymbol("VND");
        order.setPrice(15);
        assertTrue(validator.validatePrice());
    }

    @Test
    public void testValidateOrderPriceShouldFailIfZero() {
        order.setPrice(0);
        assertFalse(validator.validatePrice());
        assertEquals(OrderValidator.PRICE_NON_POSITIVE_MSG, validator.getError("price").get(0));
    }

    @Test
    public void testValidateOrderPriceShouldFailIfNegative() {
        order.setPrice(-10);
        assertFalse(validator.validatePrice());
        assertEquals(OrderValidator.PRICE_NON_POSITIVE_MSG, validator.getError("price").get(0));
    }

    @Test
    public void testValidateOrderPriceShouldPassIfLowerThanFloorPrice() {
        order.setSymbol("VND");
        order.setPrice(5);
        assertFalse(validator.validatePrice());
        assertEquals(OrderValidator.PRICE_INVALID_MSG, validator.getError("price").get(0));
    }

    @Test
    public void testValidateOrderPriceShouldPassIfHigherThanCeilingPrice() {
        order.setSymbol("ISS");
        order.setPrice(18);
        assertFalse(validator.validatePrice());
        assertEquals(OrderValidator.PRICE_INVALID_MSG, validator.getError("price").get(0));
    }

    @Test
    public void testValidateOrderQuantityShouldPassIfPositive() {
        order.setQuantity(5);
        assertTrue(validator.validateQuantity());
    }

    @Test
    public void testValidateOrderQuantityShouldPassIfZero() {
        order.setQuantity(0);
        assertFalse(validator.validateQuantity());
        assertEquals(OrderValidator.QUANTITY_NOT_POSITIVE_MSG, validator.getError("quantity").get(0));
    }

    @Test
    public void testValidateOrderQuantityShouldPassIfNegative() {
        order.setQuantity(-8);
        assertFalse(validator.validateQuantity());
        assertEquals(OrderValidator.QUANTITY_NOT_POSITIVE_MSG, validator.getError("quantity").get(0));
    }

    @Test
    public void testValidateOrderTypeShouldPassIfValid() {
        order.setOrderType("ATC");
        assertTrue(validator.validateOrderType());
    }

    @Test
    public void testValidateOrderTypeShouldPassIfInvalid() {
        order.setOrderType("AAA");
        assertFalse(validator.validateOrderType());
        assertEquals(OrderValidator.ORDER_TYPE_INVALID_MSG, validator.getError("orderType").get(0));
    }

    @Test
    public void testValidate() {
        order.setAccount("010101");
        order.setSymbol("VND");
        order.setPrice(10);
        order.setQuantity(100);
        order.setOrderType("LO");
        assertTrue(validator.validate());
    }

    @Test
    public void testValidateShouldFailIfPriceOutOfRange() {
        order.setAccount("010101");
        order.setSymbol("VND");
        order.setPrice(100);
        order.setQuantity(100);
        order.setOrderType("LO");
        assertFalse(validator.validate());
    }

}
