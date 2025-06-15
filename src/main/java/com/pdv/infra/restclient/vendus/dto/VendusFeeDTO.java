package com.pdv.infra.restclient.vendus.dto;

import java.math.BigDecimal;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusFeeDTO {

    @JsonbProperty("total")
    private BigDecimal total;

    @JsonbProperty("base")
    private BigDecimal base;

    @JsonbProperty("amount")
    private BigDecimal amount;

    @JsonbProperty("rate")
    private Integer rate;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBase() {
        return base;
    }

    public void setBase(BigDecimal base) {
        this.base = base;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    
}
