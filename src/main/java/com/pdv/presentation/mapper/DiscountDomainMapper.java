package com.pdv.presentation.mapper;

import com.pdv.domain.entities.bo.DiscountBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.DiscountDTO;

public abstract class DiscountDomainMapper {

    public static DiscountBO toBO(DiscountDTO dto) {
        if (dto == null) {
            return null;
        }

        return new DiscountBO.Builder()
                .id(new UuidVO(dto.getId()))
                .amount(dto.getAmount())
                .type(dto.getType())
                .build();
    }

    public static DiscountDTO toDTO(DiscountBO bo) {
        if (bo == null) {
            return null;
        }

        DiscountDTO discount = new DiscountDTO();

        discount.setId(bo.getId().getValue().toString());
        discount.setAmount(bo.getAmount());
        discount.setType(bo.getType());

        return discount;
    }

}
