package com.pdv.infra.restclient.vendus.mappers;

import com.pdv.infra.restclient.vendus.dto.VendusItemDTO;
import com.pdv.presentation.dto.ProductDTO;

public class VedusProductMapper {

    public static ProductDTO toDto(VendusItemDTO vendusItem) {
        var product = new ProductDTO();

        product.setQuantity(vendusItem.getQty() == null ? 1 : vendusItem.getQty().intValue());
        product.setAcquirerId(vendusItem.getId());
        product.setDescription(vendusItem.getTitle());

        if (vendusItem.getAmounts() != null) {
            product.setAmountSale(vendusItem.getAmounts().getAmountTotal());
        }

        if (vendusItem.getAmountTotal() != null) {
            product.setAmountSale(vendusItem.getAmountTotal());
        }

        if (vendusItem.getType() != null) {
            product.setType(vendusItem.getType().equals("P") ? "PRODUCT" : "SERVICE");
        }

        return product;
    }
}
