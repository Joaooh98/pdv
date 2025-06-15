package com.pdv.presentation.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.input.EncodedLoginDTO;
import com.pdv.presentation.dto.input.LoginDTO;
import com.pdv.services.EnterpriseService;
import com.pdv.services.FeeService;
import com.pdv.services.OrderService;
import com.pdv.services.ProductService;
import com.pdv.services.ProfessionalService;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class AbstractController {

    @Inject
    ProfessionalService professonalService;

    @Inject
    OrderService orderService;

    @Inject
    ProductService productService;

    @Inject
    FeeService feeService;

    @Inject
    EnterpriseService enterpriseService;

    protected LoginDTO decodeBasic64(EncodedLoginDTO encodedLogin) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedLogin.getData());
            String json = new String(decodedBytes, StandardCharsets.UTF_8);

            Jsonb jsonb = JsonbBuilder.create();

            LoginDTO login = jsonb.fromJson(json, LoginDTO.class);

            return login;
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }
    }

}
