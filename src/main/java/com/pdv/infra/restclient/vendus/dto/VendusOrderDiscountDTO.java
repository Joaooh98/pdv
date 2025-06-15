package com.pdv.infra.restclient.vendus.dto;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusOrderDiscountDTO {

    @JsonbProperty("amount")
    private String amountDiscont;

    @JsonbProperty("total")
    private String amountTotal;

    @JsonbProperty("percentage")
    private String amountPercentage;

    public String getAmountDiscont() {
        return amountDiscont;
    }

    public void setAmountDiscont(String amountDiscont) {
        this.amountDiscont = amountDiscont;
    }

    public String getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getAmountPercentage() {
        return amountPercentage;
    }

    public void setAmountPercentage(String amountPercentage) {
        this.amountPercentage = amountPercentage;
    }

}
