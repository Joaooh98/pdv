package com.pdv.infra.data.panache.mapper;

import com.pdv.domain.entities.bo.OrderFeeBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalOrderFee;

public abstract class ModalOrderFeeMapper {

    public static ModalOrderFee toModal(OrderFeeBO bo) {
        var modal = new ModalOrderFee();
        modal.setId(bo.getId().getValue());
        modal.setFee(ModalFeeMapper.toModal(bo.getFee()));
        return modal;
    }

    public static OrderFeeBO toBO(ModalOrderFee modal) {
        return new OrderFeeBO.Builder()
                .id(new UuidVO(modal.getId().toString()))
                .fee(ModalFeeMapper.toBO(modal.getFee()))
                .build();
    }

}
