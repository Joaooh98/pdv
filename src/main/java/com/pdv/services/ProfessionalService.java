package com.pdv.services;

import com.pdv.domain.usecases.professional.CreateProfessionalUseCase;
import com.pdv.domain.usecases.professional.FindProfessionalUseCase;
import com.pdv.domain.usecases.professional.RemoveProfessionalUseCase;
import com.pdv.presentation.dto.ProfessionalDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped

public class ProfessionalService extends AbstractService {

    @Transactional
    public ProfessionalDTO create(ProfessionalDTO professionalDTO) {
        return new CreateProfessionalUseCase(professionalRepository).execute(professionalDTO);
    }

    public ProfessionalDTO findByParams(String acquirerId, String userName, String password, String id) {
        if (id != null) {
            return findById(id);
        }
        
        if (acquirerId != null) {
            return new FindProfessionalUseCase(professionalRepository).execute(acquirerId, userName, password, id);
        }

        if (userName == null || password == null) {
            throw new RuntimeException("userName and password are required");
        }
        
        return new FindProfessionalUseCase(professionalRepository).execute(acquirerId, userName, password, id);
    }

    public ProfessionalDTO findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        
        return new FindProfessionalUseCase(professionalRepository).execute(null, null, null, id);
    }
    
    public ProfessionalDTO findByAcquirerId(String acquirerId) {
        if (acquirerId == null) {
            throw new IllegalArgumentException("acquirerId cannot be null");
        }
        
        return new FindProfessionalUseCase(professionalRepository).execute(acquirerId, null, null, null);
    }

    @Transactional
    public void remove(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        if (findById(id) == null) {
            throw new IllegalArgumentException("Professional not found");
        }

        new RemoveProfessionalUseCase(professionalRepository).execute(id);
    }

}
