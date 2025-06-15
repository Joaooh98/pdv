package com.pdv.domain.entities.bo;

import java.math.BigDecimal;

import com.pdv.domain.entities.enums.EnumDiscountType;
import com.pdv.domain.utils.UuidVO;

public class DiscountBO {

    private UuidVO id;

    private EnumDiscountType type;

    private BigDecimal amount;

    public DiscountBO(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.amount = builder.amount;
    }

    public static class Builder {
        private UuidVO id;
        private EnumDiscountType type;
        private BigDecimal amount;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder type(EnumDiscountType type) {
            this.type = type;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public DiscountBO build() {
            return new DiscountBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public EnumDiscountType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
