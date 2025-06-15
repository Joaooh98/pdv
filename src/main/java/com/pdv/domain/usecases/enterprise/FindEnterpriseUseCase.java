package com.pdv.domain.usecases.enterprise;

import java.util.HashMap;
import java.util.Map;

import com.pdv.domain.entities.bo.EnterpriseBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IEnterpriseRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.EnterpriseDTO;
import com.pdv.presentation.mapper.EnterpriseDomainMapper;

public class FindEnterpriseUseCase {

    private final IEnterpriseRepository enterpriseRepository;

    public FindEnterpriseUseCase(IEnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    public EnterpriseDTO execute(String username, String password) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNullOrEmpty(username)) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, "username");
        }

        if (StringUtils.isNullOrEmpty(password)) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, "password");
        }

        params.put("username", username);
        params.put("password", password);

        EnterpriseBO enterpriseBO = enterpriseRepository.find(params);

        if (enterpriseBO == null) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }

        return EnterpriseDomainMapper.toDTO(enterpriseBO);
    }

}
