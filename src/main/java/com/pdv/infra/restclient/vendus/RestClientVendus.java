package com.pdv.infra.restclient.vendus;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.pdv.infra.restclient.vendus.dto.VendusItemDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderCreateDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderDetailsDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderResponseDTO;
import com.pdv.infra.restclient.vendus.dto.VendusUserDTO;

import io.vertx.core.Closeable;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(baseUri = "https://www.vendus.pt/ws/v1.1")
public interface RestClientVendus extends Closeable {

    @GET
    @Path("/documents")
    List<VendusOrderDTO> findAllOrder(@QueryParam("api_key") String clientId,
            @QueryParam("since") String dateStart,
            @QueryParam("until") String dateEnd,
            @QueryParam("per_page") String size,
            @QueryParam("page") String until);
            
    @POST
    @Path("/documents")
    VendusOrderResponseDTO createOrder(VendusOrderCreateDTO order, @QueryParam("api_key") String clientId);

    @GET
    @Path("/documents/{id}")
    VendusOrderDetailsDTO findOrderbyId(@QueryParam("api_key") String clientId,
            @PathParam("id") String id);
    

    @GET
    @Path("/account/users")
    List<VendusUserDTO> findUsers(@QueryParam("api_key") String clientId);

    @GET
    @Path("/products")
    List<VendusItemDTO> findAllProduct(@QueryParam("api_key") String clientId, @QueryParam("per_page") String size, @QueryParam("page") String page);
}
