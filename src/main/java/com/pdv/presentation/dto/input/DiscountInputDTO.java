package com.pdv.presentation.dto.input;

import java.math.BigDecimal;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DiscountInputDTO {
    private String type; 

    private BigDecimal amount;
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
