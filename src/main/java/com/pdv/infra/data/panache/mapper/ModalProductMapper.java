package com.pdv.infra.data.panache.mapper;

import com.pdv.domain.entities.bo.ProductBO;
import com.pdv.domain.entities.enums.EnumProductType;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalProduct;

public class ModalProductMapper {

    public static ModalProduct toModal(ProductBO bo) {
        if (bo == null) {
            return null;
        }

        ModalProduct modal = new ModalProduct();

        if (bo.getId() != null) {
            modal.setId(bo.getId().getValue());
        }

        modal.setDescription(bo.getDescription());
        modal.setQuantity(bo.getQuantity());
        modal.setAcquirerId(bo.getAcquirerId());
        modal.setType(bo.getType() != null ? bo.getType().getKey() : null);
        modal.setCommission(bo.getCommission());

        if (bo.getAmountCost() != null) {
            modal.setAmountCost(bo.getAmountCost().getValue());
        }

        if (bo.getAmountSale() != null) {
            modal.setAmountSale(bo.getAmountSale().getValue());
        }

        return modal;
    }

    public static ProductBO toBO(ModalProduct modal) {
        if (modal == null) {
            return null;
        }

        return new ProductBO.Builder()
                .id(modal.getId() != null ? new UuidVO(modal.getId().toString()) : null)
                .description(modal.getDescription())
                .acquirerId(modal.getAcquirerId())
                .type(EnumProductType.parseByKey(modal.getType()))
                .commission(modal.getCommission())
                .quantity(modal.getQuantity())
                .amountCost(new Amount(modal.getAmountCost()))
                .amountSale(new Amount(modal.getAmountSale()))
                .build();
    }
}
