package com.pdv.infra.data.panache.mapper;

import com.pdv.domain.entities.bo.OrderItemBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalOrderItem;

public abstract class ModalOrderItemMapper {

    public static ModalOrderItem toModal(OrderItemBO bo) {
        ModalOrderItem modal = new ModalOrderItem();
        modal.setId(bo.getId().getValue());
        modal.setProduct(ModalProductMapper.toModal(bo.getProduct()));
        return modal;
    }

    public static OrderItemBO toBO(ModalOrderItem modal) {
        return new OrderItemBO.Builder()
                .id(new UuidVO(modal.getId().toString()))
                .product(ModalProductMapper.toBO(modal.getProduct()))
                .build();
    }
}
