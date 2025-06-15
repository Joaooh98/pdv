package com.pdv.infra.strategys.interfaces;

import com.pdv.presentation.dto.OrderDTO;

public interface IStrategyCreateOrder {

    OrderDTO create(OrderDTO order);
}
