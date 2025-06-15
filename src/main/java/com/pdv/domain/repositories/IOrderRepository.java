package com.pdv.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.pdv.domain.entities.bo.OrderBO;

public interface IOrderRepository {

    OrderBO save(OrderBO bo);

    OrderBO find(Map<String, Object> params);

    List<OrderBO> findAll(LocalDateTime start, LocalDateTime end, String provider, Integer page, Integer size, String professionalId);

    void delete(OrderBO bo);

    void merge(OrderBO bo);

}
