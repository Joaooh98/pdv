package com.pdv.domain.usecases.product;

import java.util.List;
import java.util.Map;

import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.infra.strategys.interfaces.IStrategyFindProduct;
import com.pdv.presentation.dto.ProductDTO;

public class FindProductUseCaseOnAcquirer {

    private final Map<EnumProvider, IStrategyFindProduct> acquirerFindProduct;

    public FindProductUseCaseOnAcquirer(Map<EnumProvider, IStrategyFindProduct> acquirerFindProduct) {
        this.acquirerFindProduct = acquirerFindProduct;
    }

    public List<ProductDTO> execute(Map<String, Object> params) {

        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("params");
        }

        IStrategyFindProduct strategy = acquirerFindProduct.get(params.get("provider"));

        return strategy.findAll(params);
    }
}