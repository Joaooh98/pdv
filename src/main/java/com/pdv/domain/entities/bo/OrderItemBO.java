package com.pdv.domain.entities.bo;

import com.pdv.domain.utils.UuidVO;

public class OrderItemBO {

    private UuidVO id;
    private OrderBO order;
    private ProductBO product;

    private OrderItemBO(Builder builder) {
        this.id = builder.id;
        this.order = builder.order;
        this.product = builder.product;
    }

    public static class Builder {
        private UuidVO id;
        private OrderBO order;
        private ProductBO product;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder order(OrderBO order) {
            this.order = order;
            return this;
        }

        public Builder product(ProductBO product) {
            this.product = product;
            return this;
        }

        public OrderItemBO build() {
            return new OrderItemBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public OrderBO getOrder() {
        return order;
    }

    public ProductBO getProduct() {
        return product;
    }

}
