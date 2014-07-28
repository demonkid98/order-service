package vn.com.vndirect.stock;

public class MockStockInfoService implements StockInfoService {

    public StockInfo getPrice(String code) {
        switch (code) {
        case "VND":
            return getVNDStock();
        case "ISS":
            return getISSStock();
        }
        return null;
    }

    private StockInfo getISSStock() {
        StockInfo stock = new StockInfo();
        stock.setSymbol("ISS");
        stock.setCeilingPrice(15);
        stock.setFloorPrice(5);
        return stock;
    }

    private StockInfo getVNDStock() {
        StockInfo stock = new StockInfo();
        stock.setSymbol("VND");
        stock.setCeilingPrice(20);
        stock.setFloorPrice(10);
        return stock;
    }

}
