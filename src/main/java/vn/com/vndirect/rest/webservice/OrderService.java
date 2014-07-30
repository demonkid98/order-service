package vn.com.vndirect.rest.webservice;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vn.com.vndirect.order.Order;
import vn.com.vndirect.rest.resource.OrderServiceResource;

@Path("/order")
public class OrderService {

    @Inject
    private OrderServiceResource resource;

    @POST
    @Path("/placeOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeOrder(@FormParam("account") String account, @FormParam("symbol") String symbol,
            @FormParam("price") float price, @FormParam("volume") int volume, @FormParam("type") String type) {
        Order order;
        try {
            order = resource.placeOrder(account, symbol, price, volume, type);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (NotAcceptableException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(order).build();
    }

    @POST
    @Path("/replaceOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response replaceOrder(@FormParam("orderId") String orderId, @FormParam("account") String account,
            @FormParam("symbol") String symbol, @FormParam("price") float price, @FormParam("volume") int volume,
            @FormParam("type") String type) {
        String ret;
        try {
            ret = resource.replaceOrder(orderId, account, symbol, price, volume, type);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (NotAcceptableException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(ret).build();
    }

    @POST
    @Path("/cancelOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelOrder(@FormParam("orderId") String orderId, @FormParam("account") String account,
            @FormParam("symbol") String symbol, @FormParam("price") float price, @FormParam("volume") int volume,
            @FormParam("type") String type) {
        String ret;
        try {
            ret = resource.cancelOrder(orderId, account, symbol, price, volume, type);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(ret).build();
    }

    public void setResource(OrderServiceResource resource) {
        this.resource = resource;
    }

}
