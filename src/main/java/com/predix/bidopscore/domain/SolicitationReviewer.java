package com.predix.bidopscore.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SolicitationReviewer.
 */
@Entity
@Table(name = "solicitation_reviewer")
public class SolicitationReviewer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "solicitation_id")
    private Long solicitationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public SolicitationReviewer reviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
        return this;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public SolicitationReviewer solicitationId(Long solicitationId) {
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
        SolicitationReviewer solicitationReviewer = (SolicitationReviewer) o;
        if (solicitationReviewer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitationReviewer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitationReviewer{" +
            "id=" + getId() +
            ", reviewerId=" + getReviewerId() +
            ", solicitationId=" + getSolicitationId() +
            "}";
    }
}
