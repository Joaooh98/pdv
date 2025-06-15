package com.pdv.domain.entities.bo;

import com.pdv.domain.entities.enums.EnumCoinType;
import com.pdv.domain.entities.enums.EnumPaymentType;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;

public class PaymentBO {

    private UuidVO id;

    private EnumCoinType coinType;

    private EnumPaymentType paymentType;
    
    private Amount amount;

    private PaymentBO(Builder builder) {
        this.id = builder.id;
        this.coinType = builder.coinType;
        this.paymentType = builder.paymentType;
        this.amount = builder.amount;
    }

    public static class Builder {
        private UuidVO id;
        private EnumCoinType coinType;
        private EnumPaymentType paymentType;
        private Amount amount;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder coinType(EnumCoinType coinType) {
            this.coinType = coinType;
            return this;
        }

        public Builder paymentType(EnumPaymentType paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public Builder amount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBO build() {
            return new PaymentBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public EnumCoinType getCoinType() {
        return coinType;
    }

    public EnumPaymentType getPaymentType() {
        return paymentType;
    }

    public Amount getAmount() {
        return amount;
    }
}
