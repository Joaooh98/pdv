package com.pdv.domain.usecases.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pdv.domain.entities.bo.ProductBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProductRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.ProductDTO;
import com.pdv.presentation.mapper.ProductDomainMapper;

public class FindAllProductUseCase {

     private final IProductRepository productRepository;

    public FindAllProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> execute(String enterpriseId, String type) {

        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(enterpriseId)) {
            params.put("enterprise.id", UUID.fromString(enterpriseId));
        }

        if (params == null || params.isEmpty()) {
            throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "params");
        }

        List<ProductBO> products = productRepository.findAll(params);

        if (products == null || products.isEmpty()) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }

        return products.stream().map(ProductDomainMapper::toDTO).toList();
    }

}
