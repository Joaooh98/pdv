package com.pdv.presentation.mapper;
import com.pdv.domain.entities.bo.FeeBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.FeeDTO;

public abstract class FeeDomainMapper {

    public static FeeBO toBO(FeeDTO feeDTO) {
        return new FeeBO.Builder()
                .id(new UuidVO(feeDTO.getId()))
                .description(feeDTO.getDescription())
                .percentage(feeDTO.getPercentage())
                .build();
    }

    public static FeeDTO toDTO(FeeBO feeBO) {
        FeeDTO dto = new FeeDTO();
        dto.setId(feeBO.getId().getValue().toString());
        dto.setDescription(feeBO.getDescription());
        dto.setPercentage(feeBO.getPercentage());
        return dto;
    }
}
