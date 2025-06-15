package com.pdv.domain.usecases.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.pdv.domain.entities.bo.ProductBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProductRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.ProductDTO;
import com.pdv.presentation.mapper.ProductDomainMapper;

public class UpdatedProductUseCase {

    private final IProductRepository productRepository;

    public UpdatedProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO execute(ProductDTO product) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(product.getId())) {
            params.put("id", UUID.fromString(product.getId()));
            
            List<ProductBO> response = productRepository.findAll(params);
            
            Optional<ProductBO> first = response.stream().findFirst();
            
            if (first.isPresent()) {
                ProductBO newProductBO = ProductDomainMapper.toBO(product);
                newProductBO.updateProduct(first.get().getId());
                
                productRepository.merge(newProductBO);
                
                return ProductDomainMapper.toDTO(newProductBO);
            }
        }

        throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "id");
    }
}
