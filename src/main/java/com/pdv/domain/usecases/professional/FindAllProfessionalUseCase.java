package com.pdv.domain.usecases.professional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pdv.domain.entities.bo.ProfessionalBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProfessionalRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.ProfessionalDTO;
import com.pdv.presentation.mapper.ProfessionalDomainMapper;

public class FindAllProfessionalUseCase {

    private final IProfessionalRepository professionalRepository;

    public FindAllProfessionalUseCase(IProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<ProfessionalDTO> execute(String enterpriseId) {

        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(enterpriseId)) {
            params.put("enterprise.id", UUID.fromString(enterpriseId));
        }

        if (params == null || params.isEmpty()) {
            throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "params");
        }

        List<ProfessionalBO> professionals = professionalRepository.findAll(params);

        if (professionals == null || professionals.isEmpty()) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }

        return professionals.stream().map(ProfessionalDomainMapper::toDTO).toList();
    }

}
