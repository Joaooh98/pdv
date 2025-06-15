package com.pdv.infra.restclient.vendus.dto;

import java.math.BigDecimal;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusItemDTO {

    private String id;

    @JsonbProperty("type_id")
    private String type;

    private Long qty;

    private String title;

    @JsonbProperty("reference")
    private String reference;

    @JsonbProperty("stock_control")
    private Integer stockControl;

    @JsonbProperty("amounts")
    private VendusItemAmountDTO amounts;

    @JsonbProperty("gross_price")
    private BigDecimal amountTotal;

    @JsonbProperty("discounts")
    private VendusDiscountDTO discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getStockControl() {
        return stockControl;
    }

    public void setStockControl(Integer stockControl) {
        this.stockControl = stockControl;
    }

    public VendusItemAmountDTO getAmounts() {
        return amounts;
    }

    public void setAmounts(VendusItemAmountDTO amounts) {
        this.amounts = amounts;
    }

    public VendusDiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(VendusDiscountDTO discount) {
        this.discount = discount;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

}
