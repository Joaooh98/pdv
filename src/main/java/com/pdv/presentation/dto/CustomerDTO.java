package com.pdv.presentation.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CustomerDTO {

    private String id;

    private String document;

    private String name;

    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }
}
