package com.pdv.presentation.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.pdv.domain.entities.enums.EnumOrderStatus;
import com.pdv.presentation.dto.input.OrderInputDTO;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderDTO {

    private String id;

    private String acquirerId;

    private String professionalAcquirerId;

    private String professionalId;

    private ProfessionalDTO professional;

    private CustomerDTO customer;

    private List<PaymentDTO> payments;

    private BigDecimal amount;

    private BigDecimal amountCommission;

    private BigDecimal amountNet;

    private List<OrderFeeDTO> fee;

    private List<OrderItemDTO> items;

    private String provider;

    private String dateCreate;

    private String checkoutId;

    private String type;

    private EnumOrderStatus status;

    private boolean isFinance;

    private DiscountDTO discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountCommission() {
        return amountCommission;
    }

    public void setAmountCommission(BigDecimal amountCommission) {
        this.amountCommission = amountCommission;
    }

    public List<OrderFeeDTO> getFee() {
        return fee;
    }

    public void setFee(List<OrderFeeDTO> fee) {
        this.fee = fee;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public boolean isFinance() {
        return isFinance;
    }

    public void setFinance(boolean isFinance) {
        this.isFinance = isFinance;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public BigDecimal getAmountNet() {
        return amountNet;
    }

    public void setAmountNet(BigDecimal amountNet) {
        this.amountNet = amountNet;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public EnumOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EnumOrderStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDTO discount) {
        this.discount = discount;
    }

    public OrderDTO toDTO(OrderInputDTO order) {
        this.items = new ArrayList<>();
        this.payments = new ArrayList<>();

        order.getItems().forEach(item -> this.items.add(new OrderItemDTO().toDTO(item)));
        order.getPayments().forEach(payment -> this.payments.add(new PaymentDTO().toDTO(payment)));

        this.discount = order.getDiscount() != null ? new DiscountDTO().toDTO(order.getDiscount()) : null;
        this.amount = order.getAmount();
        this.professionalId = order.getProfessionalId();
        this.provider = order.getProvider();
        this.isFinance = order.isFinance();
        this.amountCommission = BigDecimal.ZERO;
        this.amountNet = BigDecimal.ZERO;
        this.customer = order.getCustomer();

        return this;
    }

    public String getProfessionalAcquirerId() {
        return professionalAcquirerId;
    }

    public void setProfessionalAcquirerId(String professionalId) {
        this.professionalAcquirerId = professionalId;
    }

    public ProfessionalDTO getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalDTO professional) {
        this.professional = professional;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

}
