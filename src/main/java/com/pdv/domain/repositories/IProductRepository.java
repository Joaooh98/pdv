package com.pdv.domain.repositories;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pdv.domain.entities.bo.ProductBO;

public interface IProductRepository {

    ProductBO save(ProductBO bo);

    List<ProductBO> findAll(Map<String, Object> params);
    
    void merge(ProductBO bo);

        // Novos métodos para otimização
    List<ProductBO> findByAcquirerIds(Set<String> acquirerIds);
    
    List<ProductBO> findByIds(Set<String> ids);
}
