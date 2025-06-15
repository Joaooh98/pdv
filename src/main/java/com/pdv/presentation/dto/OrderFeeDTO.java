package com.pdv.presentation.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderFeeDTO {

    private String id;
    
    private OrderDTO order;
    
    private FeeDTO fee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public FeeDTO getFee() {
        return fee;
    }

    public void setFee(FeeDTO fee) {
        this.fee = fee;
    }
}
