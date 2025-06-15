package com.pdv.domain.usecases.product;

import com.pdv.domain.entities.bo.ProductBO;
import com.pdv.domain.repositories.IProductRepository;
import com.pdv.presentation.dto.ProductDTO;
import com.pdv.presentation.mapper.ProductDomainMapper;

public class CreateProductUseCase {

    private final IProductRepository productRepository;

    public CreateProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO execute(ProductDTO productDTO) {
        ProductBO response = productRepository.save(ProductDomainMapper.toBO(productDTO));
        return ProductDomainMapper.toDTO(response);
    }
}
