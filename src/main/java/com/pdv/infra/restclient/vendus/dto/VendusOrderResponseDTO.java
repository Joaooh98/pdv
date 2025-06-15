package com.pdv.infra.restclient.vendus.dto;

import java.math.BigDecimal;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusOrderResponseDTO {

    @JsonbProperty("id")
    private Long id;

    @JsonbProperty("tax_authority_id")
    private String nsu;
    
    @JsonbProperty("subtype")
    private String subtype;

    @JsonbProperty("number")
    private String number;

    @JsonbProperty("date")
    private String date;
   
    @JsonbProperty("date_supply")
    private String dateSupply;

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

    private String hash;

    private String atcud;

    @JsonbProperty("output")
    private String output;

    @JsonbProperty("output_data")
    private String outputData;

    @JsonbProperty("qrcode")
    private String qrcode;

    @JsonbProperty("qrcode_data")
    private String qrcodeData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
        this.nsu = nsu;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
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

    public String getDateSupply() {
        return dateSupply;
    }

    public void setDateSupply(String dateSupply) {
        this.dateSupply = dateSupply;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAtcud() {
        return atcud;
    }

    public void setAtcud(String atcud) {
        this.atcud = atcud;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcodeData() {
        return qrcodeData;
    }

    public void setQrcodeData(String qrcodeData) {
        this.qrcodeData = qrcodeData;
    }

    
}
