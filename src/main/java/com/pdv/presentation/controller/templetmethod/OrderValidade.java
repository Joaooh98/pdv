package com.pdv.presentation.controller.templetmethod;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.OrderDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderValidade implements IValidate<OrderDTO> {

    @Override
    public void validate(OrderDTO order) {

        if (order == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, "Order object is required");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, "Order items are required");
        }

        order.getItems().forEach(item -> {

            if (item.getProduct() != null) {
                if (StringUtils.isNullOrEmpty(item.getProduct().getId())) {
                    throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, "Product ID in Order Item is required");
                }
            }

        });

    }
}
