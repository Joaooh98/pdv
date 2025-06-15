package com.pdv.domain.entities.bo;

import com.pdv.domain.utils.UuidVO;

public class CustomerBO {

    private UuidVO id;
    private String document;
    private String name;
    private PhoneBO phoneNumber;

    private CustomerBO(Builder builder) {
        this.id = builder.id;
        this.document = builder.document;
        this.name = builder.name;
        this.phoneNumber = builder.phoneNumber;
    }

    public static class Builder {
        private UuidVO id;
        private String document;
        private String name;
        private PhoneBO phoneNumber;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phoneNumber(PhoneBO phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerBO build() {
            return new CustomerBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public PhoneBO getPhoneNumber() {
        return phoneNumber;
    }
}
