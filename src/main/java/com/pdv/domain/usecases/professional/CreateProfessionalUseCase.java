package com.pdv.domain.usecases.professional;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProfessionalRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.ProfessionalDTO;
import com.pdv.presentation.mapper.ProfessionalDomainMapper;

/*
 * Lógica de validação e de negócios
 * Validation and business logic
 * 
 */
public class CreateProfessionalUseCase {

    IProfessionalRepository professionalRepository;

    public CreateProfessionalUseCase(IProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public ProfessionalDTO execute(ProfessionalDTO professional) {

        if (StringUtils.isNullOrEmpty(professional.getName()) || 
            StringUtils.isNullOrEmpty(professional.getEmail())) {
                
            throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "Name and Email are required");
        }

        var savedCustomer = professionalRepository.save(ProfessionalDomainMapper.toBO(professional));

        return ProfessionalDomainMapper.toDTO(savedCustomer);
    }
}
