package com.pdv.infra.restclient.jlpay.repositories;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.pdv.domain.repositories.IJLpayRepository;
import com.pdv.infra.restclient.jlpay.RestClientJLPay;
import com.pdv.presentation.dto.RegisterMovimentDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JLpayRepository implements IJLpayRepository {

    @Inject
    @RestClient
    RestClientJLPay restClient;

    @Inject
    Logger logger;

    @Override
    public void registerMoviment(RegisterMovimentDTO moviment) {
        try {
            restClient.registerMoviment(moviment);
        } catch (Exception e) {
            logger.warn(e);
            throw new RuntimeException("Error registering moviment", e);
        }
    }
}
