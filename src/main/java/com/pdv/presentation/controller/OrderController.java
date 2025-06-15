package com.pdv.presentation.controller;

import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.input.OrderInputDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/checkout-api")
public class OrderController extends AbstractController {

    @POST
    @Path("/order")
    public Response persist(OrderInputDTO order) {
        OrderDTO orderDTO = orderService.create(order);
        return Response.ok(orderDTO).build();
    }

    @GET
    @Path("/order")
    public Response find(@QueryParam("id") String id, @QueryParam("provider") String provider) {
        OrderDTO order = orderService.findById(id, provider);
        return Response.ok(order).build();
    }

    @GET
    @Path("/orders")
    public Response findAll(@QueryParam("dateCreate") String dateCreate,
            @QueryParam("dateEnd") String dateEnd,
            @QueryParam("provider") String provider,
            @QueryParam("size") String size,
            @QueryParam("until") String until,
            @QueryParam("professionalId") String professionalId) {

        var orders = orderService.findAllOrders(dateCreate, dateEnd,
                provider, size, until, professionalId);

        return Response.ok(orders).build();

    }

}
