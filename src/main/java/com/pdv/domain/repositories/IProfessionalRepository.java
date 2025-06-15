package com.pdv.domain.repositories;

import java.util.List;
import java.util.Map;

import com.pdv.domain.entities.bo.ProfessionalBO;

public interface IProfessionalRepository {

    ProfessionalBO save(ProfessionalBO bo);

    ProfessionalBO find(Map<String, Object> params);

    List<ProfessionalBO> findAll(Map<String, Object> params);
    
    void delete(ProfessionalBO bo);
}
