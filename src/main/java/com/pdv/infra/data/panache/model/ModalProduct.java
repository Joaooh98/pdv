package com.pdv.infra.data.panache.model;

import java.math.BigDecimal;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ModalProduct extends PanacheEntityBase {

    @Id
    private UUID id;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "type", length = 200)
    private String type;

    @Column(name = "acquirer_id", length = 200)
    private String acquirerId;

    @Column(name = "quantity_stock", nullable = false)
    private Long quantity;

    @Column(name = "commission")
    private int commission;

    @Column(name = "amount_cost", nullable = false, precision = 15, scale = 2)
    private BigDecimal amountCost;

    @Column(name = "amount_sale", nullable = false, precision = 15, scale = 2)
    private BigDecimal amountSale;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private ModalEnterprise enterprise;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmountCost() {
        return amountCost;
    }

    public void setAmountCost(BigDecimal amountCost) {
        this.amountCost = amountCost;
    }

    public BigDecimal getAmountSale() {
        return amountSale;
    }

    public void setAmountSale(BigDecimal amountSale) {
        this.amountSale = amountSale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public void setAcquirerId(String acquirerId) {
        this.acquirerId = acquirerId;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

}
