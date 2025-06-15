package com.pdv.domain.usecases.order;

import java.util.Map;

import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.infra.strategys.interfaces.IStrategyCreateOrder;
import com.pdv.presentation.dto.OrderDTO;

public class CreateOrderAcquirerUseCase {

    private final Map<EnumProvider, IStrategyCreateOrder> createOrderRepository;

    public CreateOrderAcquirerUseCase(Map<EnumProvider, IStrategyCreateOrder> createOrderRepository) {
        this.createOrderRepository = createOrderRepository;
    }

    public OrderDTO execute(OrderDTO orderDTO) {
        EnumProvider provider = EnumProvider.parseByKey(orderDTO.getProvider());
        IStrategyCreateOrder strategy = createOrderRepository.get(provider);
        return strategy.create(orderDTO);
    }
}
