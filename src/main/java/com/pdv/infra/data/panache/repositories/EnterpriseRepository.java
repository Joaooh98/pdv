package com.pdv.infra.data.panache.repositories;

import java.util.Map;

import com.pdv.domain.entities.bo.EnterpriseBO;
import com.pdv.domain.repositories.IEnterpriseRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnterpriseRepository implements IEnterpriseRepository {

    @Override
    public EnterpriseBO find(Map<String, Object> params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

}
