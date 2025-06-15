package com.pdv.domain.repositories;

import java.util.List;
import java.util.Map;

import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.ProductDTO;
import com.pdv.presentation.dto.ProfessionalDTO;

public interface IVendusRepository {

    OrderDTO create(OrderDTO dto);

    OrderDTO findOrder(Map<String, Object> params);

    List<ProfessionalDTO> findAllProfessional(Map<String, Object> params);

    List<OrderDTO> findAllOrder(Map<String, Object> params);

    List<ProductDTO> findAllProduct(Map<String, Object> params);

}
