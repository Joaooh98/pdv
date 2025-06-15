package com.pdv.presentation.controller;

import com.pdv.presentation.dto.ProfessionalDTO;
import com.pdv.presentation.dto.input.EncodedLoginDTO;
import com.pdv.presentation.dto.input.LoginDTO;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/professional")
public class ProfessionalController extends AbstractController {

    @POST
    public Response persist(ProfessionalDTO professionalInput, @QueryParam("enterpriseId") String enterpriseId) {
        ProfessionalDTO professional = professonalService.create(professionalInput);
        return Response.ok(professional).build();
    }

    @POST
    @Path("/token")
    public Response login(EncodedLoginDTO encodedLogin) {
        LoginDTO loginDTO = decodeBasic64(encodedLogin);
        var profissional = professonalService.findByParams(null, loginDTO.getUsuario(), loginDTO.getSenha(), null);
        return Response.ok(profissional).build();
    }

    @DELETE
    @Path("/{id}")
    public Response getByUserName(@PathParam("id") String id) {
        professonalService.remove(id);
        return Response.ok().build();
    }

}
