package com.pdv.services;

import java.util.List;

import com.pdv.domain.usecases.enterprise.FindEnterpriseUseCase;
import com.pdv.domain.usecases.product.FindAllProductUseCase;
import com.pdv.domain.usecases.professional.FindAllProfessionalUseCase;
import com.pdv.presentation.dto.EnterpriseDTO;
import com.pdv.presentation.dto.ProductDTO;
import com.pdv.presentation.dto.ProfessionalDTO;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnterpriseService extends AbstractService {

    public List<ProfessionalDTO> findAllProfessionalByEnterpriseId(String enterpriseId) {
        return new FindAllProfessionalUseCase(professionalRepository).execute(enterpriseId);
    }
    
    public List<ProductDTO> findAllProductByEnterpriseId(String enterpriseId, String type) {
        return new FindAllProductUseCase(productRepository).execute(enterpriseId, type);
    }

    public EnterpriseDTO findByEnterprise(String user, String password) {
        return new FindEnterpriseUseCase(enterpriseRepository).execute(user, password);
    }
}
