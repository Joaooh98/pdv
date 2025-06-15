package com.pdv.infra.data.panache.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.pdv.domain.entities.enums.EnumCoinType;
import com.pdv.domain.entities.enums.EnumPaymentType;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_payment")
public class ModalPayment extends PanacheEntityBase {

    @Id
    private UUID id;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "coin_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumCoinType coinType;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumPaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ModalOrder order;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public EnumCoinType getCoinType() {
        return coinType;
    }

    public void setCoinType(EnumCoinType coinType) {
        this.coinType = coinType;
    }

    public EnumPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(EnumPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ModalOrder getOrder() {
        return order;
    }

    public void setOrder(ModalOrder order) {
        this.order = order;
    }

}
