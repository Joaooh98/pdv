package com.pdv.presentation.controller;

import java.util.List;

import com.pdv.presentation.dto.ProductDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/product")
public class ProductController extends AbstractController{

    @POST
    public Response persist(ProductDTO productDto) {
        ProductDTO product = productService.create(productDto);
        return Response.ok(product).build();
    }

    @GET
    public Response findAll(@QueryParam("type") String type, @QueryParam("id") String id, @QueryParam("provider") String provider) {
        if (provider != null) {
            return Response.ok(productService.findAllOnAcquirer(provider)).build();
        }
        List<ProductDTO> products = productService.findAll(type, id);
        return Response.ok(products).build();
    }

    @PATCH
    public Response updateProduct(ProductDTO productDTO) {
        ProductDTO product = productService.updateProduct(productDTO);
        return Response.ok(product).build();
    }

}
