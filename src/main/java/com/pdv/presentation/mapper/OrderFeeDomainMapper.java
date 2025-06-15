package com.pdv.presentation.mapper;

import com.pdv.domain.entities.bo.OrderFeeBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.OrderFeeDTO;

public abstract class OrderFeeDomainMapper {

    public static OrderFeeBO toBO(OrderFeeDTO orderFeeDTO) {
        if (orderFeeDTO == null) {
            return null;
        }

        return new OrderFeeBO.Builder()
            .id(new UuidVO(orderFeeDTO.getId()))
            .order(OrderDomainMapper.toBO(orderFeeDTO.getOrder()))
            .fee(FeeDomainMapper.toBO(orderFeeDTO.getFee()))
            .build();
    }

    public static OrderFeeDTO toDTO(OrderFeeBO orderFeeBO) {
        if (orderFeeBO == null) {
            return null;
        }

        OrderFeeDTO dto = new OrderFeeDTO();
        dto.setId(orderFeeBO.getId().getValue().toString());
        dto.setOrder(OrderDomainMapper.toDTO(orderFeeBO.getOrder()));
        dto.setFee(FeeDomainMapper.toDTO(orderFeeBO.getFee()));
        return dto;
    }
}
