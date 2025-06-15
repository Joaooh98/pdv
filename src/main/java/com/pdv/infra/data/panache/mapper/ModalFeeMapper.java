package com.pdv.infra.data.panache.mapper;

import com.pdv.domain.entities.bo.FeeBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalFee;

public abstract class ModalFeeMapper {

    public static ModalFee toModal(FeeBO bo) {
        ModalFee modal = new ModalFee();
        modal.setId((bo.getId().getValue()));
        modal.setDescription(bo.getDescription());
        modal.setPercentage(bo.getPercentage());
        return modal;
    }

    public static FeeBO toBO(ModalFee modal) {
        return new FeeBO.Builder()
                .id(new UuidVO(modal.getId().toString()))
                .description(modal.getDescription())
                .percentage(modal.getPercentage())
                .build();
    }
}