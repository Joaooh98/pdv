package com.pdv.services;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.usecases.customer.CreateCustomerUseCase;
import com.pdv.domain.usecases.customer.FindCustomerUseCase;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.CustomerDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CustomerService extends AbstractService {

    @Transactional
    public CustomerDTO create(CustomerDTO Customer) {
        if (Customer == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, Customer);
        }

        return new CreateCustomerUseCase(customerRepository).execute(Customer);
    }

    public CustomerDTO find(String document, String id) {
        return new FindCustomerUseCase(customerRepository).execute(document, id);
    }

}
