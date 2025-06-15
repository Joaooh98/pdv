package com.pdv.domain.usecases.order;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pdv.domain.entities.bo.OrderBO;
import com.pdv.domain.repositories.IOrderRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.mapper.OrderDomainMapper;

public class FindOrderUseCase {
    
    private final IOrderRepository orderRepository;

    public FindOrderUseCase(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO execute(String id) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(id)) {
            params.put("id", UUID.fromString(id));
        }

        OrderBO orderBO = orderRepository.find(params);

        return OrderDomainMapper.toDTO(orderBO);
    }

}
