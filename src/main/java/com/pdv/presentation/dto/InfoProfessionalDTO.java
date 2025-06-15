package com.pdv.presentation.dto;

import java.math.BigDecimal;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class InfoProfessionalDTO {

    private String name;

    private String accountIdCash;

    private String accountIdCard;

    private BigDecimal amountCash;

    private BigDecimal amountCard;
    
    private BigDecimal amountComission;

    private BigDecimal amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountIdCash() {
        return accountIdCash;
    }

    public void setAccountIdCash(String accountIdCash) {
        this.accountIdCash = accountIdCash;
    }

    public String getAccountIdCard() {
        return accountIdCard;
    }

    public void setAccountIdCard(String accountIdCard) {
        this.accountIdCard = accountIdCard;
    }

    public BigDecimal getAmountCash() {
        return amountCash;
    }

    public void setAmountCash(BigDecimal amountCash) {
        this.amountCash = amountCash;
    }

    public BigDecimal getAmountCard() {
        return amountCard;
    }

    public void setAmountCard(BigDecimal amountCard) {
        this.amountCard = amountCard;
    }

    public BigDecimal getAmountComission() {
        return amountComission;
    }

    public void setAmountComission(BigDecimal amountComission) {
        this.amountComission = amountComission;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
}
