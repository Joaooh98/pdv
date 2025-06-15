package com.pdv.domain.entities.bo;

import com.pdv.domain.utils.UuidVO;

public class OrderFeeBO {

    private UuidVO id;
    private OrderBO order;
    private FeeBO fee;

    private OrderFeeBO(Builder builder) {
        this.id = builder.id;
        this.order = builder.order;
        this.fee = builder.fee;
    }

    public static class Builder {
        private UuidVO id;
        private OrderBO order;
        private FeeBO fee;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder order(OrderBO order) {
            this.order = order;
            return this;
        }

        public Builder fee(FeeBO fee) {
            this.fee = fee;
            return this;
        }

        public OrderFeeBO build() {
            return new OrderFeeBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public OrderBO getOrder() {
        return order;
    }

    public FeeBO getFee() {
        return fee;
    }
}
