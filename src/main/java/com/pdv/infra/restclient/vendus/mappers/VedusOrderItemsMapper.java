package com.pdv.infra.restclient.vendus.mappers;

import com.pdv.infra.restclient.vendus.dto.VendusItemDTO;
import com.pdv.presentation.dto.OrderItemDTO;
import com.pdv.presentation.dto.ProductDTO;

public class VedusOrderItemsMapper {

    public static OrderItemDTO toDto(VendusItemDTO vendusItem) {

        var dto = new OrderItemDTO();
        var product = new ProductDTO();

        product.setQuantity(vendusItem.getQty() == null ? 1 : vendusItem.getQty().intValue());
        product.setAmountSale(vendusItem.getAmounts().getAmountTotal());
        product.setAcquirerId(vendusItem.getId());
        product.setDescription(vendusItem.getTitle());
        
        if (vendusItem.getType() != null) {
            product.setType(vendusItem.getType().equals("P") ? "PRODUCT" : "SERVICE");
        }

        dto.setProduct(product);
        return dto;
    }
}
