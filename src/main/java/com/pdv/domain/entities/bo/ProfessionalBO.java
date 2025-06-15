package com.pdv.domain.entities.bo;

import com.pdv.domain.utils.UuidVO;

public class ProfessionalBO {

    private UuidVO id;

    private String document;

    private String email;

    private String name;

    private String userName;

    private String password;

    private boolean isAdmin;

    private EnterpriseBO enterprise;

    private ProfessionalBO(Builder builder) {
        this.id = builder.id;
        this.document = builder.document;
        this.email = builder.email;
        this.name = builder.name;
        this.userName = builder.userName;
        this.password = builder.password;
        this.isAdmin = builder.isAdmin;
        this.enterprise = builder.enterprise;
    }

    public static class Builder {
        private UuidVO id;
        private String document;
        private String email;
        private String name;
        private String userName;
        private String password;
        private boolean isAdmin;
        private EnterpriseBO enterprise;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public Builder enterprise(EnterpriseBO enterprise) {
            this.enterprise = enterprise;
            return this;
        }

        public ProfessionalBO build() {
            return new ProfessionalBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public EnterpriseBO getEnterprise() {
        return enterprise;
    }

}
