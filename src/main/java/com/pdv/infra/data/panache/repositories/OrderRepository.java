package com.pdv.infra.data.panache.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.OrderBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IOrderRepository;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.data.panache.mapper.ModalOrderMapper;
import com.pdv.infra.data.panache.model.ModalOrder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class OrderRepository implements IOrderRepository {

    EntityManager entityManager = ModalOrder.getEntityManager();

    @Override
    public OrderBO save(OrderBO bo) {
        var modal = ModalOrderMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalOrderMapper.toBO(modal);
    }

    @Override
    public OrderBO find(Map<String, Object> params) {
        try {

            StringJoiner query = new StringJoiner(" and ");
            for (String key : params.keySet()) {
                query.add(key + " = :" + key);
            }

            ModalOrder modal = ModalOrder.find(query.toString(), params).firstResult();

            if (modal == null) {
                return null;
            }

            return ModalOrderMapper.toBO(modal);

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void delete(OrderBO bo) {
        try {
            var modal = ModalOrderMapper.toModal(bo);
            modal.delete();
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void merge(OrderBO bo) {
        var modal = ModalOrderMapper.toModal(bo);
        entityManager.merge(modal);
    }

    @Override
    public List<OrderBO> findAll(LocalDateTime start, LocalDateTime end, String provider, Integer page, Integer size,
            String professionalId) {
        try {
            int pageSize = size != null ? size : 1000;
            int pageNumber = page != null ? page : 0;
            List<ModalOrder> list = new ArrayList<>();

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

            if (provider != null) {
                queryString.append(" and provider = :provider");
                params.put("provider", provider);
            }

            if (professionalId != null) {
                queryString.append(" and professional.id = :professionalId");
                params.put("professionalId", UUID.fromString(professionalId));
            }

            list = ModalOrder.find(queryString.toString(), params).page(pageNumber, pageSize).list();

            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }

            return list.stream()
                    .map(value -> ModalOrderMapper.toBO(value))
                    .collect(Collectors.toList());

        } catch (

        Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

}
