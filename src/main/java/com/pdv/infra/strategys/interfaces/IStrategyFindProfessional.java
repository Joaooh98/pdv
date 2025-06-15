package com.pdv.infra.strategys.interfaces;

import java.util.List;
import java.util.Map;

import com.pdv.presentation.dto.ProfessionalDTO;

public interface IStrategyFindProfessional {
    
    List<ProfessionalDTO> findAll(Map<String, Object> params);
}
