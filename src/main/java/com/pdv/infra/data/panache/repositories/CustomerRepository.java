package com.pdv.infra.data.panache.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.CustomerBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.ICustomerRepository;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.data.panache.mapper.ModalCustomerMapper;
import com.pdv.infra.data.panache.model.ModalCustomer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CustomerRepository implements ICustomerRepository {

    EntityManager entityManager = ModalCustomer.getEntityManager();

    @Override
    public CustomerBO save(CustomerBO bo) {
        var modal = ModalCustomerMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalCustomerMapper.toBO(modal);
    }

    @Override
    public CustomerBO findByParams(Map<String, Object> params) {
        try {

            StringJoiner query = new StringJoiner(" and ");
            for (String key : params.keySet()) {
                query.add(key + " = :" + key);
            }

            ModalCustomer modal = ModalCustomer.find(query.toString(), params).firstResult();

            if (modal == null) {
                return null;
            }

            return ModalCustomerMapper.toBO(modal);

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void delete(CustomerBO bo) {
        try {
            var modal = ModalCustomerMapper.toModal(bo);
            modal.delete();
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void merge(CustomerBO bo) {
        var modal = ModalCustomerMapper.toModal(bo);
        entityManager.merge(modal);
    }

    @Override
    public List<CustomerBO> findAll(LocalDateTime start, LocalDateTime end, Integer page, Integer size) {
        try {
            int pageSize = size != null ? size : 1000;
            int pageNumber = page != null ? page : 0;
            List<ModalCustomer> list = new ArrayList<>();

            StringBuilder queryString = new StringBuilder("1=1");
            Map<String, Object> params = new HashMap<>();

            if (start != null) {
                queryString.append(" and createdAt >= :start ");
                params.put("start", start);
            }

            if (end != null) {
                queryString.append(" and createdAt < :end ");
                params.put("end", end);
            }

            list = ModalCustomer.find(queryString.toString(), params).page(pageNumber, pageSize).list();

            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }

            return list.stream()
                    .map(value -> ModalCustomerMapper.toBO(value))
                    .collect(Collectors.toList());

        } catch (

        Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

}
