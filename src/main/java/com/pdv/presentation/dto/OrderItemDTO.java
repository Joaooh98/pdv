package com.pdv.presentation.dto;

import com.pdv.presentation.dto.input.OrderItemInputDTO;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderItemDTO {

    private String id;

    private OrderDTO order;

    private ProductDTO product;

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

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public OrderItemDTO toDTO(OrderItemInputDTO item) {
        this.product = new ProductDTO().toDTO(item.getProduct());
        return this;
    }

}
