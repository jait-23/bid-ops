package com.predix.bidopscore.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bidders.
 */
@Entity
@Table(name = "bidders")
public class Bidders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bid_categories")
    private String bidCategories;

    @Column(name = "solicitation_id")
    private Long solicitationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Bidders name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBidCategories() {
        return bidCategories;
    }

    public Bidders bidCategories(String bidCategories) {
        this.bidCategories = bidCategories;
        return this;
    }

    public void setBidCategories(String bidCategories) {
        this.bidCategories = bidCategories;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public Bidders solicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
        return this;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bidders bidders = (Bidders) o;
        if (bidders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bidders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bidders{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bidCategories='" + getBidCategories() + "'" +
            ", solicitationId=" + getSolicitationId() +
            "}";
    }
}
