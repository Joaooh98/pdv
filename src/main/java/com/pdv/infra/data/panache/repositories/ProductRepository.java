package com.pdv.infra.data.panache.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.ProductBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProductRepository;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.data.panache.mapper.ModalProductMapper;
import com.pdv.infra.data.panache.model.ModalProduct;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ProductRepository implements IProductRepository {

    EntityManager entityManager = ModalProduct.getEntityManager();

    @Override
    public ProductBO save(ProductBO bo) {
        var modal = ModalProductMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalProductMapper.toBO(modal);
    }

    @Override
    public List<ProductBO> findAll(Map<String, Object> params) {
        try {

            StringJoiner query = new StringJoiner(" and ");
            for (String key : params.keySet()) {
                query.add(key + " = :" + key);
            }

            List<ModalProduct> list = ModalProduct.find(query.toString(), params).list();

            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }

            return list.stream()
                    .map(value -> ModalProductMapper.toBO(value))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void merge(ProductBO bo) {
        var modal = ModalProductMapper.toModal(bo);
        entityManager.merge(modal);
    }

        /**
     * Busca produtos por múltiplos acquirerIds de uma vez
     * Método otimizado para evitar N+1 queries
     */
    public List<ProductBO> findByAcquirerIds(Set<String> acquirerIds) {
        try {
            if (acquirerIds == null || acquirerIds.isEmpty()) {
                return new ArrayList<>();
            }

            // Usando Panache query
            List<ModalProduct> products = ModalProduct
                .find("acquirerId in ?1", acquirerIds)
                .list();

            return products.stream()
                    .map(ModalProductMapper::toBO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, 
                "Error finding products by acquirer IDs: " + e.getMessage());
        }
    }

    /**
     * Busca produtos por múltiplos IDs de uma vez
     * Método otimizado para evitar N+1 queries
     */

    public List<ProductBO> findByIds(Set<String> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return new ArrayList<>();
            }

            // Usando Panache query
            List<ModalProduct> products = ModalProduct
                .find("id in ?1", ids)
                .list();

            return products.stream()
                    .map(ModalProductMapper::toBO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, 
                "Error finding products by IDs: " + e.getMessage());
        }
    }

}
