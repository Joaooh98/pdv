package com.pdv.presentation.mapper;

import java.math.BigDecimal;

import com.pdv.domain.entities.bo.ProductBO;
import com.pdv.domain.entities.enums.EnumProductType;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.ProductDTO;

public abstract class ProductDomainMapper {

    public static ProductBO toBO(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        return new ProductBO.Builder()
                .id(new UuidVO(productDTO.getId()))
                .acquirerId(productDTO.getAcquirerId())
                .description(productDTO.getDescription())
                .quantity(productDTO.getQuantity().longValue())
                .type(EnumProductType.parseByKey(productDTO.getType()))
                .commission(productDTO.getCommission())
                .amountCost(productDTO.getAmountCost() != null ? new Amount(productDTO.getAmountCost())
                        : new Amount(BigDecimal.ZERO))
                .amountSale(productDTO.getAmountSale() != null ? new Amount(productDTO.getAmountSale())
                        : new Amount(BigDecimal.ZERO))
                .build();
    }

    public static ProductDTO toDTO(ProductBO productBO) {
        if (productBO == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productBO.getId().getValue().toString());
        productDTO.setDescription(productBO.getDescription());
        productDTO.setAcquirerId(productBO.getAcquirerId());
        productDTO.setType(productBO.getType().getKey());
        productDTO.setQuantity( productBO.getQuantity() != null ? productBO.getQuantity().intValue() : null);
        productDTO.setCommission(productBO.getCommission());
        productDTO.setAmountCost(
                productBO.getAmountCost() != null ? productBO.getAmountCost().getValue() : BigDecimal.ZERO);
        productDTO.setAmountSale(
                productBO.getAmountSale() != null ? productBO.getAmountSale().getValue() : BigDecimal.ZERO);

        return productDTO;
    }
}
