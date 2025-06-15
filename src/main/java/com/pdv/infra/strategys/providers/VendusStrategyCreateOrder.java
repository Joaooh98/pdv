package com.pdv.infra.strategys.providers;

import com.pdv.domain.repositories.IVendusRepository;
import com.pdv.infra.strategys.interfaces.IStrategyCreateOrder;
import com.pdv.presentation.dto.OrderDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VendusStrategyCreateOrder implements IStrategyCreateOrder{

    @Inject
    IVendusRepository vendusRepository;

    @Override
    public OrderDTO create(OrderDTO order) {
        return vendusRepository.create(order);
    }

}
