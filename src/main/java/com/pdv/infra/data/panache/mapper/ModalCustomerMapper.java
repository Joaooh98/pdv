package com.pdv.infra.data.panache.mapper;

import com.pdv.domain.entities.bo.CustomerBO;
import com.pdv.domain.entities.bo.PhoneBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalCustomer;

public class ModalCustomerMapper {

    public static ModalCustomer toModal(CustomerBO customer) {
        if (customer == null) {
            return null;
        }

        ModalCustomer modal = new ModalCustomer();
        modal.setId(customer.getId().getValue());
        modal.setName(customer.getName());
        modal.setDocument(customer.getDocument());
        modal.setPhoneNumber(customer.getPhoneNumber() != null ? customer.getPhoneNumber().getNumber() : null);

        return modal;
    }

    public static CustomerBO toBO(ModalCustomer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerBO.Builder()
                .id(new UuidVO(customer.getId().toString()))
                .name(customer.getName())
                .document(customer.getDocument())
                .phoneNumber(customer.getPhoneNumber() != null ? new PhoneBO.Builder()
                            .number(customer.getPhoneNumber())
                            .build() : null)
                .build();
    }
}
