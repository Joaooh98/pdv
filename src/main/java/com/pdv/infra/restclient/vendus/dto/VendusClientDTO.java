package com.pdv.infra.restclient.vendus.dto;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusClientDTO {

    @JsonbProperty("name")
    private String name;

    @JsonbProperty("fiscal_id")
    private String fiscalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFiscalId() {
        return fiscalId;
    }

    public void setFiscalId(String fiscalId) {
        this.fiscalId = fiscalId;
    }

}
