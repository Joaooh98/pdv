package com.pdv.domain.entities.bo;

import java.util.Date;
import java.util.List;

import com.pdv.domain.utils.UuidVO;

public class EnterpriseBO {

    private UuidVO id;
    private String acquirerId;
    private String name;
    private String document;
    private List<ProfessionalBO> partners;
    private Date openDate;

    private EnterpriseBO(Builder builder) {
        this.id = builder.id;
        this.acquirerId = builder.acquirerId;
        this.name = builder.name;
        this.document = builder.document;
        this.partners = builder.partners;
        this.openDate = builder.openDate;
    }

    public static class Builder {
        private UuidVO id;
        private String acquirerId;
        private String name;
        private String document;
        private List<ProfessionalBO> partners;
        private Date openDate;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder acquirerId(String acquirerId) {
            this.acquirerId = acquirerId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public Builder partners(List<ProfessionalBO> partners) {
            this.partners = partners;
            return this;
        }

        public Builder openDate(Date openDate) {
            this.openDate = openDate;
            return this;
        }

        public EnterpriseBO build() {
            return new EnterpriseBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public List<ProfessionalBO> getPartners() {
        return partners;
    }

    public Date getOpenDate() {
        return openDate;
    }
}
