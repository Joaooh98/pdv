package com.pdv.domain.entities.bo;

import com.pdv.domain.utils.UuidVO;

public class FeeBO {

    private UuidVO id;
    private String description;
    private int percentage;

    private FeeBO(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.percentage = builder.percentage;
    }

    public static class Builder {

        private UuidVO id;
        private String description;
        private int percentage;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder percentage(int percentage) {
            this.percentage = percentage;
            return this;
        }

        public FeeBO build() {
            return new FeeBO(this);
        }

    }

    public void update(UuidVO id) {
        this.id = id;
    }

    public UuidVO getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPercentage() {
        return percentage;
    }

}
