package com.predix.bidopscore.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PrimaryEvaluations.
 */
@Entity
@Table(name = "primary_evaluations")
public class PrimaryEvaluations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "solicitation_id")
    private Long solicitationId;

    @Column(name = "doc_url")
    private String docURL;

    @Column(name = "category_solicitations")
    private String categorySolicitations;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public PrimaryEvaluations userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public PrimaryEvaluations solicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
        return this;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }

    public String getDocURL() {
        return docURL;
    }

    public PrimaryEvaluations docURL(String docURL) {
        this.docURL = docURL;
        return this;
    }

    public void setDocURL(String docURL) {
        this.docURL = docURL;
    }

    public String getCategorySolicitations() {
        return categorySolicitations;
    }

    public PrimaryEvaluations categorySolicitations(String categorySolicitations) {
        this.categorySolicitations = categorySolicitations;
        return this;
    }

    public void setCategorySolicitations(String categorySolicitations) {
        this.categorySolicitations = categorySolicitations;
    }

    public String getStatus() {
        return status;
    }

    public PrimaryEvaluations status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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
        PrimaryEvaluations primaryEvaluations = (PrimaryEvaluations) o;
        if (primaryEvaluations.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), primaryEvaluations.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrimaryEvaluations{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", solicitationId=" + getSolicitationId() +
            ", docURL='" + getDocURL() + "'" +
            ", categorySolicitations='" + getCategorySolicitations() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
