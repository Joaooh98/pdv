package com.pdv.infra.data.panache.model;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_fee")
public class ModalOrderFee extends PanacheEntityBase {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ModalOrder order;

    @ManyToOne
    @JoinColumn(name = "fee_id", nullable = false)
    private ModalFee fee;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ModalOrder getOrder() {
        return order;
    }

    public void setOrder(ModalOrder order) {
        this.order = order;
    }

    public ModalFee getFee() {
        return fee;
    }

    public void setFee(ModalFee fee) {
        this.fee = fee;
    }
}
