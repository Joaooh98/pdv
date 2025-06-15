package com.pdv.infra.data.panache.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.FeeBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IFeeRepository;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.data.panache.mapper.ModalFeeMapper;
import com.pdv.infra.data.panache.model.ModalFee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager; 

@ApplicationScoped
public class FeeRepository implements IFeeRepository {

    EntityManager entityManager = ModalFee.getEntityManager();

    @Override
    public FeeBO save(FeeBO bo) {
        var modal = ModalFeeMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalFeeMapper.toBO(modal);
    }

    @Override
    public List<FeeBO> findAll(Map<String, Object> params) {
        try {

            StringJoiner query = new StringJoiner(" and ");
            for (String key : params.keySet()) {
                query.add(key + " = :" + key);
            }

            List<ModalFee> list = ModalFee.find(query.toString(), params).list();

            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }

            return list.stream()
                    .map(value -> ModalFeeMapper.toBO(value))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void merge(FeeBO bo) {
        var modal = ModalFeeMapper.toModal(bo);
        entityManager.merge(modal);
    }

}
