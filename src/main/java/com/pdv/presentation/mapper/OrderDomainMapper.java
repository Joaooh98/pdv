package com.pdv.presentation.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.OrderBO;
import com.pdv.domain.entities.bo.OrderFeeBO;
import com.pdv.domain.entities.bo.OrderItemBO;
import com.pdv.domain.entities.bo.PaymentBO;
import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;
import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.OrderFeeDTO;
import com.pdv.presentation.dto.OrderItemDTO;
import com.pdv.presentation.dto.PaymentDTO;

public abstract class OrderDomainMapper {

    public static OrderBO toBO(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        return new OrderBO.Builder()
            .id(new UuidVO(orderDTO.getId()))
            .customer(CustomerDomainMapper.toBO(orderDTO.getCustomer()))
            .payments(toPaymentBOList(orderDTO.getPayments()))
            .items(toOrderItemBOList(orderDTO.getItems()))
            .fees(toOrderFeeBOList(orderDTO.getFee()))
            .amount(new Amount(orderDTO.getAmount()))
            .amountCommission(orderDTO.getAmountCommission() != null ? new Amount(orderDTO.getAmountCommission()) : null)
            .isFinance(orderDTO.isFinance())
            .provider(EnumProvider.parseByKey(orderDTO.getProvider()))
            .discount(DiscountDomainMapper.toBO(orderDTO.getDiscount()))
            .professional(ProfessionalDomainMapper.toBO(orderDTO.getProfessional()))
            .dataCreate(orderDTO.getDateCreate())
            .acquirerId(orderDTO.getAcquirerId())
            .build();
    }

    public static OrderDTO toDTO(OrderBO orderBO) {
        if (orderBO == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderBO.getId().getValue().toString());
        orderDTO.setCustomer(CustomerDomainMapper.toDTO(orderBO.getCustomer()));
        orderDTO.setPayments(toPaymentDTOList(orderBO.getPayments()));
        orderDTO.setItems(toOrderItemDTOList(orderBO.getItems()));
        orderDTO.setFee(toOrderFeeDTOList(orderBO.getFees()));
        orderDTO.setAmount(orderBO.getAmount().getValue());
        orderDTO.setAmountCommission(orderBO.getAmountCommission() != null ? orderBO.getAmountCommission().getValue(): BigDecimal.ZERO);
        orderDTO.setFinance(orderBO.isFinance());
        orderDTO.setProvider(orderBO.getProvider().getKey());
        orderDTO.setDiscount(DiscountDomainMapper.toDTO(orderBO.getDiscount()));
        orderDTO.setProfessional(ProfessionalDomainMapper.toDTO(orderBO.getProfessional()));
        orderDTO.setDateCreate(orderBO.getDataCreate());
        orderDTO.setAcquirerId(orderBO.getAcquirerId());

        return orderDTO;
    }

    private static List<PaymentBO> toPaymentBOList(List<PaymentDTO> payments) {
        return payments == null ? null : payments.stream()
            .map(PaymentDomainMapper::toBO)
            .collect(Collectors.toList());
    }

    private static List<PaymentDTO> toPaymentDTOList(List<PaymentBO> payments) {
        return payments == null ? null : payments.stream()
            .map(PaymentDomainMapper::toDTO)
            .collect(Collectors.toList());
    }

    private static List<OrderItemBO> toOrderItemBOList(List<OrderItemDTO> items) {
        return items == null ? null : items.stream()
            .map(OrderItemDomainMapper::toBO)
            .collect(Collectors.toList());
    }

    private static List<OrderItemDTO> toOrderItemDTOList(List<OrderItemBO> items) {
        return items == null ? null : items.stream()
            .map(OrderItemDomainMapper::toDTO)
            .collect(Collectors.toList());
    }

    private static List<OrderFeeBO> toOrderFeeBOList(List<OrderFeeDTO> fees) {
        return fees == null ? null : fees.stream()
            .map(OrderFeeDomainMapper::toBO)
            .collect(Collectors.toList());
    }

    private static List<OrderFeeDTO> toOrderFeeDTOList(List<OrderFeeBO> fees) {
        return fees == null ? null : fees.stream()
            .map(OrderFeeDomainMapper::toDTO)
            .collect(Collectors.toList());
    }

}
