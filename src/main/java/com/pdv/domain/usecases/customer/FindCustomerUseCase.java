package com.pdv.domain.usecases.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pdv.domain.entities.bo.CustomerBO;
import com.pdv.domain.repositories.ICustomerRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.presentation.dto.CustomerDTO;
import com.pdv.presentation.mapper.CustomerDomainMapper;

public class FindCustomerUseCase {

    private final ICustomerRepository customerRepository;

    public FindCustomerUseCase(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO execute(String document, String id) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(document)) {
            params.put("document", document);
        }

        if (StringUtils.isNotNullOrEmpty(id)) {
            params.put("id", UUID.fromString(id));
        }

        CustomerBO response = customerRepository.findByParams(params);

        if (response == null) {
            return null;
        }

        return CustomerDomainMapper.toDTO(response);
    }
}
