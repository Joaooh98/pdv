package com.pdv.infra.restclient.vendus.mappers;

import com.pdv.infra.restclient.vendus.dto.VendusClientDTO;
import com.pdv.presentation.dto.CustomerDTO;

public class VedusOrderClientMapper {

    public static CustomerDTO toDto(VendusClientDTO vendusDto) {
        var dto = new CustomerDTO();

        dto.setName(vendusDto.getName());
        dto.setDocument(vendusDto.getFiscalId());
        return dto;
    }   

}
