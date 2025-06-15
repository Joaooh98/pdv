package com.pdv.infra.restclient.jlpay;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.pdv.presentation.dto.RegisterMovimentDTO;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:8999")
public interface RestClientJLPay {

    @POST
    @Path("/account/moviment")
    void registerMoviment(RegisterMovimentDTO moviment);
}
