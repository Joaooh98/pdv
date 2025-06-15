package com.pdv.domain.repositories;

import java.util.List;
import java.util.Map;

import com.pdv.domain.entities.bo.FeeBO;

public interface IFeeRepository {

    FeeBO save(FeeBO bo);

    List<FeeBO> findAll(Map<String, Object> params);
    
    void merge(FeeBO bo);
}
