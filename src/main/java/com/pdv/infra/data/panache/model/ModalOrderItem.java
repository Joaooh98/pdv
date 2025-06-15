package com.pdv.infra.data.panache.model;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
public class ModalOrderItem extends PanacheEntityBase {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ModalOrder order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ModalProduct product;

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

    public ModalProduct getProduct() {
        return product;
    }

    public void setProduct(ModalProduct product) {
        this.product = product;
    }

}
