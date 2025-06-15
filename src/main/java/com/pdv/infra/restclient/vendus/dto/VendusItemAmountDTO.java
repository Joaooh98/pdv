package com.pdv.infra.restclient.vendus.dto;

import java.math.BigDecimal;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusItemAmountDTO {

    @JsonbProperty("gross_total")
    private BigDecimal amountTotal;

    @JsonbProperty("gross_unit")
    private BigDecimal amountUnit;

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public BigDecimal getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(BigDecimal amountUnit) {
        this.amountUnit = amountUnit;
    }
}
