package com.pdv.presentation.mapper;

import com.pdv.domain.entities.bo.ProfessionalBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.ProfessionalDTO;

public abstract class ProfessionalDomainMapper {

    public static ProfessionalBO toBO(ProfessionalDTO dto) {
        return new ProfessionalBO.Builder()
                .id(new UuidVO(dto.getId())) 
                .document(dto.getDocument())
                .email(dto.getEmail())
                .name(dto.getName())
                .userName(dto.getUsername())
                .password(dto.getPassword())
                .isAdmin(dto.isAdmin())
                .enterprise(EnterpriseDomainMapper.toBO(dto.getEnterprise()))
                .build();
    }

    public static ProfessionalDTO toDTO(ProfessionalBO bo) {
        ProfessionalDTO dto = new ProfessionalDTO();
        dto.setId(bo.getId().getValue().toString()); 
        dto.setDocument(bo.getDocument());
        dto.setEmail(bo.getEmail());
        dto.setName(bo.getName());
        dto.setUsername(bo.getUserName());
        dto.setPassword(bo.getPassword());
        dto.setAdmin(bo.isAdmin());
        dto.setEnterprise(EnterpriseDomainMapper.toDTO(bo.getEnterprise()));
        return dto;
    }

}
