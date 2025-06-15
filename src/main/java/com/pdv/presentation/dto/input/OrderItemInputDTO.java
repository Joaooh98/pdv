package com.pdv.presentation.dto.input;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderItemInputDTO {

    private ProductInputDTO product;

    public ProductInputDTO getProduct() {
        return product;
    }

    public void setProduct(ProductInputDTO product) {
        this.product = product;
    }

}
