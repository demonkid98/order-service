package vn.com.vndirect.error;

import vn.com.vndirect.order.Order;
import vn.com.vndirect.order.OrderType;
import vn.com.vndirect.stock.StockInfo;
import vn.com.vndirect.stock.StockInfoService;

public class OrderValidator extends Validator {

    public static final String ACCOUNT_EMPTY_MSG = "Account must not be empty.";
    public static final String SYMBOL_EMPTY_MSG = "Symbol must not be empty.";
    public static final String SYMBOL_NOT_EXISTED_MSG = "This symbol does not exist.";
    public static final String PRICE_NON_POSITIVE_MSG = "Price must be greater than 0.";
    public static final String PRICE_INVALID_MSG = "Price must be in floor - ceiling range.";
    public static final String QUANTITY_NOT_POSITIVE_MSG = "Quantity must be greater than 0.";
    public static final String ORDER_TYPE_INVALID_MSG = "Invalid order type.";

    private Order order;
    private StockInfoService stockService;

    public OrderValidator(Order order) {
        this.order = order;
        this.errors = new BaseError();
    }

    public void setStockService(StockInfoService stockService) {
        this.stockService = stockService;
    }

    @Override
    public boolean validate() {
        return validateAccount() && validateSymbol() && validatePrice() && validateQuantity() && validateOrderType();
    }

    protected boolean validateAccount() {
        if (order.getAccount() == null || order.getAccount().equals("")) {
            errors.add("account", ACCOUNT_EMPTY_MSG);
            return false;
        }
        return true;
    }

    protected boolean validateSymbol() {
        if (order.getSymbol() == null || order.getSymbol().equals("")) {
            errors.add("symbol", SYMBOL_EMPTY_MSG);
            return false;
        }
        if (getStockInfo() == null) {
            errors.add("symbol", SYMBOL_NOT_EXISTED_MSG);
            return false;
        }
        return true;
    }

    protected boolean validatePrice() {
        if (order.getPrice() <= 0) {
            errors.add("price", PRICE_NON_POSITIVE_MSG);
            return false;
        }

        StockInfo stock = getStockInfo();
        if (stock != null) {
            if (order.getPrice() < stock.getFloorPrice() || order.getPrice() > stock.getCeilingPrice()) {
                errors.add("price", PRICE_INVALID_MSG);
                return false;
            }
        }
        return true;
    }

    protected boolean validateQuantity() {
        if (order.getQuantity() <= 0) {
            errors.add("quantity", QUANTITY_NOT_POSITIVE_MSG);
            return false;
        }
        return true;
    }

    protected boolean validateOrderType() {
        try {
            OrderType.valueOf(order.getOrderType());
        } catch (IllegalArgumentException _) {
            errors.add("orderType", ORDER_TYPE_INVALID_MSG);
            return false;
        }
        return true;
    }

    private StockInfo getStockInfo() {
        return stockService.getPrice(order.getSymbol());
    }

}
