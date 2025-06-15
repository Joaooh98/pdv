package com.pdv.presentation.mapper;

import com.pdv.domain.entities.bo.OrderItemBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.OrderItemDTO;

public abstract class OrderItemDomainMapper {

    public static OrderItemBO toBO(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            return null;
        }

        return new OrderItemBO.Builder()
                .id(new UuidVO(orderItemDTO.getId()))
                .order(OrderDomainMapper.toBO(orderItemDTO.getOrder()))
                .product(ProductDomainMapper.toBO(orderItemDTO.getProduct()))
                .build();
    }

    public static OrderItemDTO toDTO(OrderItemBO orderItemBO) {
        if (orderItemBO == null) {
            return null;
        }

        OrderItemDTO dto = new OrderItemDTO();

        dto.setId(orderItemBO.getId().getValue().toString());
        dto.setOrder(OrderDomainMapper.toDTO(orderItemBO.getOrder()));
        dto.setProduct(ProductDomainMapper.toDTO(orderItemBO.getProduct()));
        return dto;
    }
}
