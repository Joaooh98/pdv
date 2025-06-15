package com.pdv.domain.usecases.order;

import java.util.Map;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.strategys.interfaces.IStrategyFindOrder;
import com.pdv.presentation.dto.OrdersDTO;

public class FindAllOrderAcquirerUseCase {

    private final Map<EnumProvider, IStrategyFindOrder> findOrderRepository;

    public FindAllOrderAcquirerUseCase(Map<EnumProvider, IStrategyFindOrder> findOrderRepository) {
        this.findOrderRepository = findOrderRepository;
    }

    public OrdersDTO execute(Map<String, Object> params) {

        if (params == null || params.isEmpty()) {
            throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "params");
        }

        IStrategyFindOrder strategy = findOrderRepository.get(params.get("provider"));
        
        return strategy.findAll(params);
    }

}
