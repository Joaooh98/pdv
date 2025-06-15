package com.pdv.domain.entities.bo;

public class PhoneBO {

    private String number;

    private PhoneBO(Builder builder) {
        this.number = builder.number;
    }

    public static class Builder {
        private String number;

        public Builder number(String number){
            this.number = number;
            return this;
        }

        public PhoneBO build() {
            return new PhoneBO(this);
        }
    }

    public String getNumber() {
        return number;
    }

    
}
