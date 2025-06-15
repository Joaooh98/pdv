package com.pdv.infra.strategys.interfaces;

import java.util.Map;

import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.OrdersDTO;

public interface IStrategyFindOrder {
    
    OrdersDTO findAll(Map<String, Object> params);

    OrderDTO findById(Map<String, Object> params);
}
