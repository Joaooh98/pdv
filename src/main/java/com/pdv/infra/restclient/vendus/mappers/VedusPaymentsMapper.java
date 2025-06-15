package com.pdv.infra.restclient.vendus.mappers;

import com.pdv.infra.restclient.vendus.dto.VendusPaymentDTO;
import com.pdv.presentation.dto.PaymentDTO;

public class VedusPaymentsMapper {

    public static PaymentDTO toDto(VendusPaymentDTO vendusDto) {
        var dto = new PaymentDTO();

        dto.setAcquirerId(vendusDto.getId().toString());
        dto.setPaymentType(vendusDto.getTitle());
        dto.setAmount(vendusDto.getAmount());

        return dto;
    }

}
