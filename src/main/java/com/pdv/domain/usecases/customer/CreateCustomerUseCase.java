package com.pdv.domain.usecases.customer;

import com.pdv.domain.entities.bo.CustomerBO;
import com.pdv.domain.repositories.ICustomerRepository;
import com.pdv.presentation.dto.CustomerDTO;
import com.pdv.presentation.mapper.CustomerDomainMapper;

public class CreateCustomerUseCase {

    private final ICustomerRepository customerRepository;

    public CreateCustomerUseCase(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO execute(CustomerDTO dto) {
        CustomerBO response = customerRepository.save(CustomerDomainMapper.toBO(dto));
        return CustomerDomainMapper.toDTO(response);
    }
}
