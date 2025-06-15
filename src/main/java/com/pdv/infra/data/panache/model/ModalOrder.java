package com.pdv.infra.data.panache.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_sale")
public class ModalOrder extends PanacheEntityBase {

    @Id
    private UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ModalPayment> payments;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ModalOrderItem> productSales;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ModalOrderFee> orderFees;

    @Column(name = "is_finance", nullable = false)
    private boolean isFinance;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "acquirerId")
    private String acquirerId;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = true)
    private ModalProfessional professional;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private ModalCustomer customer;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ModalPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<ModalPayment> payments) {
        this.payments = payments;
    }

    public List<ModalOrderItem> getProductSales() {
        return productSales;
    }

    public void setProductSales(List<ModalOrderItem> productSales) {
        this.productSales = productSales;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<ModalOrderFee> getOrderFees() {
        return orderFees;
    }

    public void setOrderFees(List<ModalOrderFee> orderFees) {
        this.orderFees = orderFees;
    }

    public boolean isFinance() {
        return isFinance;
    }

    public void setFinance(boolean isFinance) {
        this.isFinance = isFinance;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ModalProfessional getProfessional() {
        return professional;
    }

    public void setProfessional(ModalProfessional professional) {
        this.professional = professional;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public ModalCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(ModalCustomer customer) {
        this.customer = customer;
    }

}
