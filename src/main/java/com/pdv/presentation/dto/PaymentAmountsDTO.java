package com.pdv.presentation.dto;

import java.math.BigDecimal;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PaymentAmountsDTO {
    private BigDecimal cashAmount = BigDecimal.ZERO;
    private BigDecimal cardAmount = BigDecimal.ZERO;

    public void addCashAmount(BigDecimal amount) {
        this.cashAmount = this.cashAmount.add(amount);
    }

    public void addCardAmount(BigDecimal amount) {
        this.cardAmount = this.cardAmount.add(amount);
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public BigDecimal getCardAmount() {
        return cardAmount;
    }

    public BigDecimal getTotal() {
        return cashAmount.add(cardAmount);
    }
}
