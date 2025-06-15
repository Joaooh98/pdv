package com.pdv.domain.usecases.professional;

import java.util.List;
import java.util.Map;

import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.infra.strategys.interfaces.IStrategyFindProfessional;
import com.pdv.presentation.dto.ProfessionalDTO;

public class FindAllProfessionalAcquirerUseCase {

    private final Map<EnumProvider, IStrategyFindProfessional> findProfessionalRepository;

    public FindAllProfessionalAcquirerUseCase(Map<EnumProvider, IStrategyFindProfessional> findProfessionalRepository) {
        this.findProfessionalRepository = findProfessionalRepository;
    }

    public List<ProfessionalDTO> execute(Map<String, Object> params) {
        
        IStrategyFindProfessional strategy = findProfessionalRepository.get(params.get("provider"));
        List<ProfessionalDTO> allProfessional = strategy.findAll(params);

        return allProfessional;
    }

}
