package com.pdv.presentation.dto;

import java.math.BigDecimal;

import com.pdv.presentation.dto.input.ProductInputDTO;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ProductDTO {

    private String id;

    private String acquirerId;

    private String description;

    private Integer quantity;

    private int commission;

    private String type;

    private BigDecimal amountCost;

    private BigDecimal amountSale;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public BigDecimal getAmountCost() {
        return amountCost;
    }

    public void setAmountCost(BigDecimal amountCost) {
        this.amountCost = amountCost;
    }

    public BigDecimal getAmountSale() {
        return amountSale;
    }

    public void setAmountSale(BigDecimal amountSale) {
        this.amountSale = amountSale;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public ProductDTO toDTO(ProductInputDTO product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.quantity = product.getQuantity();
        this.amountCost = product.getAmount();
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
