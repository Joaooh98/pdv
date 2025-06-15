package com.pdv.presentation.dto;

import java.util.Date;
import java.util.List;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EnterpriseDTO {

    private String id;

    private String acquirerId;

    private String name;

    private String document;

    private List<ProfessionalDTO> partners;

    private String clientId;

    private Date openDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<ProfessionalDTO> getPartners() {
        return partners;
    }

    public void setPartners(List<ProfessionalDTO> partners) {
        this.partners = partners;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
