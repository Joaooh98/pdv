package com.pdv.presentation.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.EnterpriseBO;
import com.pdv.domain.entities.bo.ProfessionalBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.EnterpriseDTO;
import com.pdv.presentation.dto.ProfessionalDTO;

public class EnterpriseDomainMapper {

    public static EnterpriseBO toBO(EnterpriseDTO dto) {
        if (dto == null) {
            return null;
        }

        return new EnterpriseBO.Builder()
                .id(new UuidVO(dto.getId()))
                .acquirerId(dto.getAcquirerId())
                .name(dto.getName())
                .document(dto.getDocument())
                .partners(toBOList(dto.getPartners()))
                .openDate(dto.getOpenDate())
                .build();
    }

    public static EnterpriseDTO toDTO(EnterpriseBO bo) {
        if (bo == null) {
            return null;
        }

        EnterpriseDTO dto = new EnterpriseDTO();
        dto.setId(bo.getId().toString());
        dto.setAcquirerId(bo.getAcquirerId());
        dto.setName(bo.getName());
        dto.setDocument(bo.getDocument());
        dto.setPartners(toDTOList(bo.getPartners()));
        dto.setOpenDate(bo.getOpenDate());

        return dto;
    }

    private static List<ProfessionalBO> toBOList(List<ProfessionalDTO> professionalDTOs) {
        if (professionalDTOs == null) {
            return null;
        }
        return professionalDTOs.stream()
                .map(ProfessionalDomainMapper::toBO)
                .collect(Collectors.toList());
    }

    private static List<ProfessionalDTO> toDTOList(List<ProfessionalBO> professionalBOs) {
        if (professionalBOs == null) {
            return null;
        }
        return professionalBOs.stream()
                .map(ProfessionalDomainMapper::toDTO)
                .collect(Collectors.toList());
    }
}
