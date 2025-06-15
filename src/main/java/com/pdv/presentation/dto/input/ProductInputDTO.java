package com.pdv.presentation.dto.input;

import java.math.BigDecimal;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ProductInputDTO {

    private String id;

    private String description;

    private BigDecimal amount;

    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}