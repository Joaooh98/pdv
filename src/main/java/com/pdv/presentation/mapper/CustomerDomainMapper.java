package com.pdv.presentation.mapper;

import com.pdv.domain.entities.bo.CustomerBO;
import com.pdv.domain.entities.bo.PhoneBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.CustomerDTO;

public abstract class CustomerDomainMapper {

    public static CustomerBO toBO(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        return new CustomerBO.Builder()
                .id(new UuidVO(dto.getId()))
                .document(dto.getDocument())
                .name(dto.getName())
                .phoneNumber(dto.getPhone() != null ? new PhoneBO.Builder()
                        .number(dto.getPhone())
                        .build() : null)
                .build();
    }

    public static CustomerDTO toDTO(CustomerBO bo) {
        if (bo == null) {
            return null;
        }

        CustomerDTO dto = new CustomerDTO();
        dto.setId(bo.getId().getValue().toString());
        dto.setDocument(bo.getDocument());
        dto.setName(bo.getName());
        dto.setPhone(bo.getPhoneNumber() != null ? bo.getPhoneNumber().getNumber() : null);

        return dto;
    }

}
