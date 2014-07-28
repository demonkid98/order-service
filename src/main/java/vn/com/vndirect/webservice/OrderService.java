package vn.com.vndirect.webservice;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/order")
public class OrderService {

    @POST
    @Path("/placeOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public String placeOrder(@FormParam("account") String account, @FormParam("symbol") String symbol,
            @FormParam("price") float price, @FormParam("volume") int volume, @FormParam("type") String type) {

        return "";
    }

    @POST
    @Path("/replaceOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public String replaceOrder(@FormParam("orderId") String orderId, @FormParam("account") String account,
            @FormParam("symbol") String symbol, @FormParam("price") float price, @FormParam("volume") int volume,
            @FormParam("type") String type) {

        return "";
    }

    @POST
    @Path("/cancelOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public String cancelOrder(@FormParam("orderId") String orderId) {

        return "";
    }
}
