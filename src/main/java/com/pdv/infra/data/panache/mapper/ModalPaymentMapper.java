package com.pdv.infra.data.panache.mapper;

import com.pdv.domain.entities.bo.PaymentBO;
import com.pdv.domain.entities.enums.EnumCoinType;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalPayment;

public abstract class ModalPaymentMapper {

    public static ModalPayment toModal(PaymentBO bo) {
        var modal = new ModalPayment();

        modal.setId((bo.getId().getValue()));
        modal.setAmount(bo.getAmount().getValue());
        modal.setCoinType(bo.getCoinType() == null ? EnumCoinType.EUR : bo.getCoinType());
        modal.setPaymentType(bo.getPaymentType());

        return modal;
    }

    public static PaymentBO toBO(ModalPayment modal) {
        return new PaymentBO.Builder()
                .id(new UuidVO(modal.getId().toString()))
                .amount(new Amount(modal.getAmount()))
                .coinType(modal.getCoinType())
                .paymentType(modal.getPaymentType())
                .build();
    }
}