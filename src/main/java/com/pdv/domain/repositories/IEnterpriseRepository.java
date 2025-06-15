package com.pdv.domain.repositories;

import java.util.Map;

import com.pdv.domain.entities.bo.EnterpriseBO;

public interface IEnterpriseRepository {

    EnterpriseBO find(Map<String, Object> params);
}
