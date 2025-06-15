package com.pdv.domain.usecases.professional;

import java.util.HashMap;
import java.util.UUID;

import com.pdv.domain.entities.bo.ProfessionalBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProfessionalRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;

import java.util.Map;

public class RemoveProfessionalUseCase {

    private final IProfessionalRepository professionalRepository;

    public RemoveProfessionalUseCase(IProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public void execute(String id) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(id)) {
            params.put("id", UUID.fromString(id));
        }

        ProfessionalBO professionalBO = professionalRepository.find(params);

        if (professionalBO == null) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }

        professionalRepository.delete(professionalBO);

    }

}
