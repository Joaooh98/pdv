package com.pdv.presentation.mapper;

import com.pdv.domain.entities.bo.PaymentBO;
import com.pdv.domain.entities.enums.EnumCoinType;
import com.pdv.domain.entities.enums.EnumPaymentType;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.PaymentDTO;

public abstract class PaymentDomainMapper {

    public static PaymentBO toBO(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        return new PaymentBO.Builder()
            .id(new UuidVO(paymentDTO.getId()))
            .coinType(paymentDTO.getCoinType() == null ? EnumCoinType.EUR : EnumCoinType.parseByKey(paymentDTO.getCoinType()))
            .paymentType(EnumPaymentType.parseByKey(paymentDTO.getPaymentType()))
            .amount(new Amount(paymentDTO.getAmount()))
            .build();
    }
    
    public static PaymentDTO toDTO(PaymentBO paymentBO) {

        if (paymentBO == null) {
            return null;
        }

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId(paymentBO.getId().getValue().toString());
        paymentDTO.setAmount(paymentBO.getAmount().getValue());
        paymentDTO.setPaymentType(paymentBO.getPaymentType().getKey());
        paymentDTO.setCoinType(paymentBO.getCoinType().getKey());

        return paymentDTO;
    }
}
