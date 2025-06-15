package com.pdv.infra.restclient.vendus.dto;

import java.math.BigDecimal;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusPaymentDTO {

    @JsonbProperty("id")
    private Long id;

    @JsonbProperty("title")
    private String title;

    @JsonbProperty("amount")
    private BigDecimal amount;

    @JsonbProperty("date_due")
    private String dateDue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
}
