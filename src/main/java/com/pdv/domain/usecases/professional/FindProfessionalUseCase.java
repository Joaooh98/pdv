package com.pdv.domain.usecases.professional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pdv.domain.entities.bo.ProfessionalBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProfessionalRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.ProfessionalDTO;
import com.pdv.presentation.mapper.ProfessionalDomainMapper;

public class FindProfessionalUseCase {
    
    private final IProfessionalRepository professionalRepository;

    public FindProfessionalUseCase(IProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public ProfessionalDTO execute(String acquirerId, String userName, String password, String id) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(id)) {
            params.put("id", UUID.fromString(id));
        }
        
        if (StringUtils.isNotNullOrEmpty(userName)) {
            params.put("username", userName);
        }

        if (StringUtils.isNotNullOrEmpty(acquirerId)) {
            params.put("acquirerId", acquirerId);
        }

        if (StringUtils.isNotNullOrEmpty(password)) {
            params.put("password", password);
        }

        ProfessionalBO professionalBO = professionalRepository.find(params);

        if (professionalBO == null) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }

        return ProfessionalDomainMapper.toDTO(professionalBO);
    }
    
}
