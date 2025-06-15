package com.pdv.presentation.controller;

import java.util.List;

import com.pdv.presentation.dto.FeeDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/checkout-api")
public class FeeController extends AbstractController {

    @POST
    @Path("/fee")
    public Response persist(FeeDTO fee) {
        var feeDTO = feeService.create(fee);
        return Response.ok(feeDTO).build();
    }

    @GET
    @Path("/fee")
    public Response findAll(@QueryParam("description") String description, @QueryParam("id") String id) {
        List<FeeDTO> fees = feeService.findAll(description, id);
        return Response.ok(fees).build();
    }

    @PATCH
    @Path("/fee")
    public Response updateProduct(FeeDTO fee) {
        FeeDTO feeDTO = feeService.updateFee(fee);
        return Response.ok(feeDTO).build();
    }
}
