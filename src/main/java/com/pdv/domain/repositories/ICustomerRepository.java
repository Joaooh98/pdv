package com.pdv.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.pdv.domain.entities.bo.CustomerBO;

public interface ICustomerRepository {

    CustomerBO save(CustomerBO bo);

    List<CustomerBO> findAll(LocalDateTime start, LocalDateTime end, Integer page, Integer size);

    void merge(CustomerBO bo);

    void delete(CustomerBO bo);

    CustomerBO findByParams(Map<String, Object> params);
}
