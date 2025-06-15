package com.pdv.infra.strategys.providers;

import java.util.List;
import java.util.Map;

import com.pdv.domain.repositories.IVendusRepository;
import com.pdv.infra.strategys.interfaces.IStrategyFindProduct;
import com.pdv.presentation.dto.ProductDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VendusStrategyFindProduct implements IStrategyFindProduct {

    @Inject
    private IVendusRepository vendusRepository;

    @Override
    public List<ProductDTO> findAll(Map<String, Object> params) {
        return vendusRepository.findAllProduct(params);
    }

}
