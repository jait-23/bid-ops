package com.predix.bidopscore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.predix.bidopscore.domain.Solicitations;

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

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(mappedBy = "bidders")
    @JsonIgnore
    private Solicitations bidders;
    
    @Column(name = "solicitation_id", updatable=false, insertable=false)
    private Long solicitationId;

    @OneToOne
    @JoinColumn(unique = true)
    private User jhi_user;

    @OneToOne
    @JoinColumn(unique = true)
    private Solicitations solicitations;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Solicitations getSolicitation() {
        return solicitations;
    }

    public void setSolicitation(Solicitations solicitations) {
        this.solicitations = solicitations;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
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

    public Long getUserId() {
        return userId;
    }

    public Bidders userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Solicitations getBidders() {
        return bidders;
    }

    public Bidders bidders(Solicitations solicitations) {
        this.bidders = solicitations;
        return this;
    }

    public void setBidders(Solicitations solicitations) {
        this.bidders = solicitations;
    }

    public User getJhi_user() {
        return jhi_user;
    }

    public Bidders jhi_user(User jhi_user) {
        this.jhi_user = jhi_user;
        return this;
    }

    public void setJhi_user(User jhi_user) {
        this.jhi_user = jhi_user;
    }

    public Solicitations getSolicitations() {
        return solicitations;
    }

    public Bidders solicitations(Solicitations solicitations) {
        this.solicitations = solicitations;
        return this;
    }

    public void setSolicitations(Solicitations solicitations) {
        this.solicitations = solicitations;
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
            ", solicitationId=" + getSolicitationId() +
            ", name='" + getName() + "'" +
            ", bidCategories='" + getBidCategories() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
