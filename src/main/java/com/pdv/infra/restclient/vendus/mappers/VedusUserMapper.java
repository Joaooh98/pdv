package com.pdv.infra.restclient.vendus.mappers;

import com.pdv.infra.restclient.vendus.dto.VendusUserDTO;
import com.pdv.presentation.dto.ProfessionalDTO;

public class VedusUserMapper {

    public static ProfessionalDTO toDto(VendusUserDTO vendusDto) {
        var dto = new ProfessionalDTO();

        dto.setAcquirerId(vendusDto.getId());
        dto.setEmail(vendusDto.getEmail());
        dto.setName(vendusDto.getName());

        return dto;
    }

    public static VendusUserDTO toVendusDto(ProfessionalDTO dto) {
        var vendusDto = new VendusUserDTO();

        return vendusDto;
    }

}
