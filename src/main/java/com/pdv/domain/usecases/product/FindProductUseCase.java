package com.pdv.domain.usecases.product;

import java.util.ArrayList;
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

public class FindProductUseCase {
    
    private final IProductRepository productRepository;

    public FindProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> execute(String type, String id, String acquirerId, boolean isExeception) {
        Map<String, Object> params = new HashMap<>();

        if (type == null && id == null && acquirerId == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, "type, id or acquirerId");
        }

        if (StringUtils.isNotNullOrEmpty(type)) {
            params.put("type", type);
        }
       
        if (StringUtils.isNotNullOrEmpty(id)) {
            params.put("id", UUID.fromString(id));
        }

        if (StringUtils.isNotNullOrEmpty(acquirerId)) {
            params.put("acquirerId", acquirerId);
        }

        List<ProductBO> response = productRepository.findAll(params);

        if (response == null || response.isEmpty() && isExeception) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }

        if (response == null || response.isEmpty() && !isExeception) {
           return null;
        }

        List<ProductDTO> products = new ArrayList<>();
        response.forEach(e -> products.add(ProductDomainMapper.toDTO(e)));

        return products;
    }
}
