package com.pdv.infra.strategys.providers;

import java.util.List;
import java.util.Map;

import com.pdv.domain.repositories.IVendusRepository;
import com.pdv.infra.strategys.interfaces.IStrategyFindProfessional;
import com.pdv.presentation.dto.ProfessionalDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VendusStrategyFindProfessional implements IStrategyFindProfessional {

    @Inject
    private IVendusRepository vendusRepository;

    @Override
    public List<ProfessionalDTO> findAll(Map<String, Object> params) {
        return vendusRepository.findAllProfessional(params);
    }

}
