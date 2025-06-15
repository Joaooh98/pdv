package com.pdv.presentation.dto.input;


import java.math.BigDecimal;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PaymentInputDTO {

    private String paymentType;

    private BigDecimal amount;

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

}
