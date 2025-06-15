package com.pdv.domain.usecases.order;

import java.util.Map;

import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.infra.strategys.interfaces.IStrategyFindOrder;
import com.pdv.presentation.dto.OrderDTO;

public class FindOrderAcquirerUseCase {

    private final Map<EnumProvider, IStrategyFindOrder> findOrderRepository;

    public FindOrderAcquirerUseCase(Map<EnumProvider, IStrategyFindOrder> findOrderRepository) {
        this.findOrderRepository = findOrderRepository;
    }

    public OrderDTO execute(Map<String, Object> params) {
        IStrategyFindOrder strategy = findOrderRepository.get(params.get("provider"));
        return strategy.findById(params);
    }

}
