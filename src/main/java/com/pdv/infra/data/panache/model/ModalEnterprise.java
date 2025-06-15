package com.pdv.infra.data.panache.model;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "enterprise")
public class ModalEnterprise extends PanacheEntityBase {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "client_Id", nullable = false)
    private String clientId;

    @Column(name = "open_Date", nullable = false)
    private String openDate;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    private List<ModalProfessional> professionals;

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

}
