package com.pdv.presentation.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class RegisterMovimentDTO {

    private String value;

    private String typeMoviment;

    private String accountId;

    private String description;

    private String typePayment;

    public RegisterMovimentDTO(String value, String typeMoviment, String accountId, String description,
            String typePayment) {
        this.value = value;
        this.typeMoviment = typeMoviment;
        this.accountId = accountId;
        this.description = description;
        this.typePayment = typePayment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTypeMoviment() {
        return typeMoviment;
    }

    public void setTypeMoviment(String typeMoviment) {
        this.typeMoviment = typeMoviment;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(String typeCoin) {
        this.typePayment = typeCoin;
    }
 
}
