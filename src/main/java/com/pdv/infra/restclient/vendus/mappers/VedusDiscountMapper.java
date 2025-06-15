package com.pdv.infra.restclient.vendus.mappers;

import java.math.BigDecimal;

import com.pdv.domain.entities.enums.EnumDiscountType;
import com.pdv.infra.restclient.vendus.dto.VendusDiscountDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderDiscountDTO;
import com.pdv.presentation.dto.DiscountDTO;

public class VedusDiscountMapper {

    public static DiscountDTO toDto(VendusDiscountDTO vendusDto) {
        var dto = new DiscountDTO();

        if (vendusDto.getPercentage() != null) {
            dto.setType(vendusDto.getPercentage().compareTo(BigDecimal.ZERO) > 0 ? EnumDiscountType.PORCENTAGE
                    : EnumDiscountType.AMOUNT);
        } else {
            dto.setType(EnumDiscountType.AMOUNT);

        }

        dto.setAmount(vendusDto.getAmount());

        return dto;
    }

    public static DiscountDTO toDto(VendusOrderDiscountDTO vendusDto) {
        var dto = new DiscountDTO();
        
        if (vendusDto.getAmountPercentage() != null && vendusDto.getAmountPercentage().compareTo("0") > 0) {
            dto.setType(EnumDiscountType.PORCENTAGE);
            dto.setAmount(new BigDecimal(vendusDto.getAmountDiscont()));
            return dto;
        }

        dto.setType(EnumDiscountType.AMOUNT);
        dto.setAmount(new BigDecimal(vendusDto.getAmountTotal()));
        return dto;

    }

}
