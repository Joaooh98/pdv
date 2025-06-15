package com.pdv.presentation.dto;

import java.math.BigDecimal;

import com.pdv.domain.entities.enums.EnumDiscountType;
import com.pdv.presentation.dto.input.DiscountInputDTO;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DiscountDTO {
    private String id;

    private EnumDiscountType type; 

    private BigDecimal amount;
    
    public EnumDiscountType getType() {
        return type;
    }

    public void setType(EnumDiscountType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DiscountDTO toDTO(DiscountInputDTO discount) {
        this.type = EnumDiscountType.parseByValue(discount.getType());
        this.amount = discount.getAmount();
        return this;
    }
}
