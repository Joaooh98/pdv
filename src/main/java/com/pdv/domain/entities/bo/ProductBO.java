package com.pdv.domain.entities.bo;

import java.math.BigDecimal;

import com.pdv.domain.entities.enums.EnumProductType;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;

public class ProductBO {

    private UuidVO id;

    private String description;

    private String acquirerId;

    private EnumProductType type;

    private Long quantity;

    private int commission;

    private Amount amountCost;

    private Amount amountSale;

    private ProductBO(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.acquirerId = builder.acquirerId;
        this.quantity = builder.quantity;
        this.commission = builder.commission;
        this.amountCost = builder.amountCost;
        this.amountSale = builder.amountSale;
        this.type = builder.type;
    }

    public static class Builder {
        private UuidVO id;
        private String description;
        private String acquirerId;
        private Long quantity;
        private int commission;
        private Amount amountCost;
        private Amount amountSale;
        private EnumProductType type;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder acquirerId(String acquirerId) {
            this.acquirerId = acquirerId;
            return this;
        }

        public Builder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder commission(int commission) {
            this.commission = commission;
            return this;
        }

        public Builder amountCost(Amount amountCost) {
            this.amountCost = amountCost;
            return this;
        }

        public Builder amountSale(Amount amountSale) {
            this.amountSale = amountSale;
            return this;
        }

        public Builder type(EnumProductType type) {
            this.type = type;
            return this;
        }

        public ProductBO build() {
            return new ProductBO(this);
        }
    }

    public BigDecimal calcCommission() {
        return amountSale.getValue().multiply(BigDecimal.valueOf(commission)).divide(BigDecimal.valueOf(100));
    }

    public void updateProduct(UuidVO id) {
        this.id = id;
    }

    public UuidVO getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getQuantity() {
        return quantity;
    }

    public int getCommission() {
        return commission;
    }

    public Amount getAmountCost() {
        return amountCost;
    }

    public Amount getAmountSale() {
        return amountSale;
    }

    public EnumProductType getType() {
        return type;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

}
