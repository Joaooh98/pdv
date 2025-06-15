package com.pdv.infra.strategys.providers;

import java.util.List;
import java.util.Map;

import com.pdv.domain.repositories.IVendusRepository;
import com.pdv.infra.strategys.interfaces.IStrategyFindOrder;
import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.OrdersDTO;
import com.pdv.presentation.dto.PaymentAmountsDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VendusStrategyFindOrder implements IStrategyFindOrder {

    private static final String PAYMENT_TYPE_CASH = "Dinheiro";
    private static final String PAYMENT_TYPE_CARD = "Multibanco";

    @Inject
    private IVendusRepository vendusRepository;

    @Override
    public OrderDTO findById(Map<String, Object> params) {
        return vendusRepository.findOrder(params);
    }

    @Override
    public OrdersDTO findAll(Map<String, Object> params) {
        List<OrderDTO> orders = vendusRepository.findAllOrder(params);
        var amounts = calculateAmounts(orders);
        return handleResponse(orders, amounts);

    }

    private PaymentAmountsDTO calculateAmounts(List<OrderDTO> orders) {
        PaymentAmountsDTO amounts = new PaymentAmountsDTO();

        orders.forEach(order -> order.getPayments().forEach(payment -> {
            if (PAYMENT_TYPE_CASH.equals(payment.getPaymentType())) {
                amounts.addCashAmount(payment.getAmount());
            } else if (PAYMENT_TYPE_CARD.equals(payment.getPaymentType())) {
                amounts.addCardAmount(payment.getAmount());
            }
        }));

        return amounts;
    }

    private OrdersDTO handleResponse(List<OrderDTO> orders, PaymentAmountsDTO amounts) {
        return OrdersDTO.builder()
                .orders(orders)
                .totalAmount(amounts.getTotal())
                .totalAmountCard(amounts.getCardAmount())
                .totalAmountCash(amounts.getCashAmount())
                .totalOrders(orders.size())
                .build();
    }
}
