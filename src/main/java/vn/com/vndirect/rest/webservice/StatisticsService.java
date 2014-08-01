package vn.com.vndirect.rest.webservice;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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
        List<Map<String, String>> result;
        try {
            result = resource.getTopOrders();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/topAccounts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response topAccounts() {
        List<Map<String, String>> result;
        try {
            result = resource.getTopAccounts();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

}
