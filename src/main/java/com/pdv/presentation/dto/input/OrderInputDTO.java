package com.pdv.presentation.dto.input;

import java.math.BigDecimal;
import java.util.List;

import com.pdv.presentation.dto.CustomerDTO;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderInputDTO {

    private BigDecimal amount;

    private DiscountInputDTO discount;

    private List<PaymentInputDTO> payments;

    private List<OrderItemInputDTO> items;

    private boolean finance;
    
    private String professionalId;

    private String provider;

    private String custumerId;

    private CustomerDTO customer;

    public boolean isFinance() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public DiscountInputDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountInputDTO discount) {
        this.discount = discount;
    }

    public List<PaymentInputDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInputDTO> payments) {
        this.payments = payments;
    }

    public List<OrderItemInputDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemInputDTO> items) {
        this.items = items;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCustumerId() {
        return custumerId;
    }

    public void setCustumerId(String custumerId) {
        this.custumerId = custumerId;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

}
