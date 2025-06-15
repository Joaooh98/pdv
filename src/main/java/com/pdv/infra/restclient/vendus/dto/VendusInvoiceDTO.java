package com.pdv.infra.restclient.vendus.dto;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusInvoiceDTO {

    @JsonbProperty("document_number")
    private String documentNumber;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

}
