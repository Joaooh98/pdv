package com.pdv.presentation.dto;

import java.math.BigDecimal;

import com.pdv.presentation.dto.input.PaymentInputDTO;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PaymentDTO {

    private String id;

    private String acquirerId;

    private String coinType;

    private String paymentType;

    private BigDecimal amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public PaymentDTO toDTO(PaymentInputDTO payment) {
        this.paymentType = payment.getPaymentType();
        this.amount = payment.getAmount();
        return this;
    }
}
