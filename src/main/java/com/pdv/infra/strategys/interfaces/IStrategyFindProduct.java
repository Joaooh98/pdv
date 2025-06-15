package com.pdv.infra.strategys.interfaces;

import java.util.List;
import java.util.Map;

import com.pdv.presentation.dto.ProductDTO;

public interface IStrategyFindProduct {
    
    List<ProductDTO> findAll(Map<String, Object> params);
}
