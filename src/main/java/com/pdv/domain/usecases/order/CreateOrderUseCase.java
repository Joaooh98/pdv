package com.pdv.domain.usecases.order;

import java.util.Map;

import com.pdv.domain.entities.bo.OrderBO;
import com.pdv.domain.repositories.IOrderRepository;
import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.mapper.OrderDomainMapper;

public class CreateOrderUseCase {

    private final IOrderRepository orderRepository;

    public CreateOrderUseCase(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO execute(OrderDTO orderDTO) {

        if (orderDTO.getAcquirerId() != null) {
            OrderBO orderInternal = orderRepository.find(Map.of("acquirerId", orderDTO.getAcquirerId()));
            if (orderInternal != null) {
                return OrderDomainMapper.toDTO(orderInternal);
            }

        }

        OrderBO orderBO = OrderDomainMapper.toBO(orderDTO);

        orderBO.businessRuleItems();
        orderBO.businessRulePayments();

        OrderBO responsOrderBO = orderRepository.save(orderBO);

        return OrderDomainMapper.toDTO(responsOrderBO);
    }
}
