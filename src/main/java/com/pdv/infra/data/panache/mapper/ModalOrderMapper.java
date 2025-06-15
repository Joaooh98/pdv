package com.pdv.infra.data.panache.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.OrderBO;
import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalOrder;
import com.pdv.infra.data.panache.util.ListUtilModal;

public abstract class ModalOrderMapper {

    public static ModalOrder toModal(OrderBO bo) {
        if (bo == null) {
            return null;
        }

        ModalOrder modal = new ModalOrder();

        modal.setId(bo.getId().getValue());
        modal.setAcquirerId(bo.getAcquirerId());
        modal.setPayments(bo.getPayments().stream().map(
                ModalPaymentMapper::toModal).collect(Collectors.toList()));
        modal.setProductSales(bo.getItems().stream().map(
                ModalOrderItemMapper::toModal).collect(Collectors.toList()));
        modal.setOrderFees(bo.getFees().stream().map(
                ModalOrderFeeMapper::toModal).collect(Collectors.toList()));

        modal.setFinance(bo.isFinance());
        modal.setAmount(bo.getAmount().getValue());
        modal.setProfessional(ModalProfessionalMapper.toModal(bo.getProfessional()));
        modal.setCustomer(ModalCustomerMapper.toModal(bo.getCustomer()));

        ListUtilModal.setEntityInList(modal.getPayments(), p -> p.setOrder(modal));
        ListUtilModal.setEntityInList(modal.getProductSales(), p -> p.setOrder(modal));
        ListUtilModal.setEntityInList(modal.getOrderFees(), p -> p.setOrder(modal));

        modal.setProvider(bo.getProvider().getKey());

        if (bo.getDataCreate() != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            modal.setCreatedAt(LocalDateTime.parse(bo.getDataCreate(), formatter));
        }

        return modal;
    }

    public static OrderBO toBO(ModalOrder modal) {
        if (modal == null) {
            return null;
        }

        return new OrderBO.Builder()
                .id(new UuidVO(modal.getId().toString()))
                .amount(new Amount(modal.getAmount()))
                .isFinance(modal.isFinance())
                .payments(modal.getPayments().stream().map(
                        ModalPaymentMapper::toBO).collect(Collectors.toList()))
                .items(modal.getProductSales().stream().map(
                        ModalOrderItemMapper::toBO).collect(Collectors.toList()))
                .provider(EnumProvider.parseByKey(modal.getProvider()))
                .professional(modal.getProfessional() != null
                        ? ModalProfessionalMapper.toBO(modal.getProfessional())
                        : null)
                .fees(modal.getOrderFees().stream().map(
                        ModalOrderFeeMapper::toBO).collect(Collectors.toList()))
                .acquirerId(modal.getAcquirerId())
                .dataCreate(modal.getCreatedAt().toString())
                .customer(modal.getCustomer() != null
                        ? ModalCustomerMapper.toBO(modal.getCustomer())
                        : null)
                .build();
    }

}
