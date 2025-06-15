package com.pdv.presentation.dto.input;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EncodedLoginDTO {
    private String data;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
