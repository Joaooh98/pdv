package com.pdv.presentation.controller;

import com.pdv.presentation.dto.EnterpriseDTO;
import com.pdv.presentation.dto.input.EncodedLoginDTO;
import com.pdv.presentation.dto.input.LoginDTO;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/enterprise")
public class EnterpriseController extends AbstractController {

    @POST
    public Response persist(EnterpriseDTO enterprise) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response getEnterprise(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @GET
    @Path("/users")
    public Response getUsers(@QueryParam("id") String id) {
        return Response.ok(enterpriseService.findAllProfessionalByEnterpriseId(id)).build();
    }

    @GET
    @Path("/products")
    public Response getProducts(@QueryParam("id") String id, @QueryParam("type") String type) {
        return Response.ok(enterpriseService.findAllProductByEnterpriseId(id, type)).build();
    }

    @POST
    @Path("/token")
    public Response login(EncodedLoginDTO encodedLogin) {
        LoginDTO loginDTO = decodeBasic64(encodedLogin);
        var enterprise = enterpriseService.findByEnterprise(loginDTO.getUsuario(), loginDTO.getSenha());
        return Response.ok(enterprise).build();
    }

}
