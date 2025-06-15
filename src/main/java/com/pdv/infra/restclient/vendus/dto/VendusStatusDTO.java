package com.pdv.infra.restclient.vendus.dto;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusStatusDTO {
    
    @JsonbProperty("id")
    private String id;

    @JsonbProperty("date")
    private String date;

    @JsonbProperty("user_id")
    private Long userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
}
