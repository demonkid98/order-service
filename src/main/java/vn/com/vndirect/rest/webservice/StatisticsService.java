package vn.com.vndirect.rest.webservice;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vn.com.vndirect.rest.resource.StatisticsServiceResource;

@Path("/stats")
public class StatisticsService {

    @Inject
    private StatisticsServiceResource resource;

    @GET
    @Path("/topOrders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response topOrders() {
        return Response.status(Response.Status.OK).entity(resource.getTopOrders()).build();
    }

    @GET
    @Path("/topAccounts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response topAccounts() {
        return Response.status(Response.Status.OK).entity(resource.getTopAccounts()).build();
    }

}
