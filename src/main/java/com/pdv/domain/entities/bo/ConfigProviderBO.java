package com.pdv.domain.entities.bo;

import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.domain.utils.UuidVO;

public class ConfigProviderBO {

    private UuidVO id;

    private String description;

    private EnumProvider provider;

    private ProfessionalBO professional;

    private String clientId;

    private String clientSecret;

    private ConfigProviderBO(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.provider = builder.provider;
        this.professional = builder.professional;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
    }

    public static class Builder {
        private UuidVO id;
        private String description;
        private EnumProvider provider;
        private ProfessionalBO professional;
        private String clientId;
        private String clientSecret;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder provider(EnumProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder professional(ProfessionalBO professional) {
            this.professional = professional;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public ConfigProviderBO build() {
            return new ConfigProviderBO(this);
        }
    }

    public UuidVO getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public EnumProvider getProvider() {
        return provider;
    }

    public ProfessionalBO getProfessional() {
        return professional;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    
}
