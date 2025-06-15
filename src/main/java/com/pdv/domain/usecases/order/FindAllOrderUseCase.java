package com.pdv.domain.usecases.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pdv.domain.entities.bo.OrderBO;
import com.pdv.domain.repositories.IOrderRepository;
import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.OrdersDTO;
import com.pdv.presentation.dto.PaymentAmountsDTO;
import com.pdv.presentation.mapper.OrderDomainMapper;

public class FindAllOrderUseCase {

    private final IOrderRepository orderRepository;

    private static final String PAYMENT_TYPE_CASH = "CASH";

    private static final String PAYMENT_TYPE_CARD = "CARD";

    public FindAllOrderUseCase(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrdersDTO execute(LocalDateTime dateStart, LocalDateTime dateEnd, String provider, Integer page,
            Integer size, String professionalId) {

        List<OrderBO> allOrders = orderRepository.findAll(dateStart, dateEnd, provider, page, size, professionalId);

        List<OrderDTO> orderDTOs = allOrders.isEmpty() ? new ArrayList<>()
                : allOrders.stream().map(OrderDomainMapper::toDTO).toList();

        PaymentAmountsDTO amounts = calculateAmounts(orderDTOs);
        return handleResponse(orderDTOs, amounts);

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
