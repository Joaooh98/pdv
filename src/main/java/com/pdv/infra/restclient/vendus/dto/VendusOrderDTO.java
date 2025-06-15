package com.pdv.infra.restclient.vendus.dto;

import java.math.BigDecimal;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusOrderDTO {

    @JsonbProperty("id")
    private Long id;
    
    @JsonbProperty("number")
    private String number;
    
    @JsonbProperty("date")
    private String date;
    
    @JsonbProperty("store_id")
    private String storeId;
    
    @JsonbProperty("register_id")
    private String checkoutId;
    
    @JsonbProperty("date_due")
    private String dateDue;

    @JsonbProperty("system_time")
    private String systemTime;

    @JsonbProperty("local_time")
    private String localTime;

    @JsonbProperty("amount_gross")
    private BigDecimal amountGross;

    @JsonbProperty("amount_net")
    private BigDecimal amountNet;

    @JsonbProperty("type")
    private String type;

    private String status;

    private String totalUpaid;

    private String paymentStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public BigDecimal getAmountGross() {
        return amountGross;
    }

    public void setAmountGross(BigDecimal amountGross) {
        this.amountGross = amountGross;
    }

    public BigDecimal getAmountNet() {
        return amountNet;
    }

    public void setAmountNet(BigDecimal amountNet) {
        this.amountNet = amountNet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalUpaid() {
        return totalUpaid;
    }

    public void setTotalUpaid(String totalUpaid) {
        this.totalUpaid = totalUpaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

   

    
}
