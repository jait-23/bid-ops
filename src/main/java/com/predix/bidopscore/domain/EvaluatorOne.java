package com.predix.bidopscore.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EvaluatorOne.
 */
@Entity
@Table(name = "evaluator_one")
public class EvaluatorOne implements Serializable {

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

    @Column(name = "category_solicitation")
    private String categorySolicitation;

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

    public EvaluatorOne userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public EvaluatorOne solicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
        return this;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }

    public String getDocURL() {
        return docURL;
    }

    public EvaluatorOne docURL(String docURL) {
        this.docURL = docURL;
        return this;
    }

    public void setDocURL(String docURL) {
        this.docURL = docURL;
    }

    public String getCategorySolicitation() {
        return categorySolicitation;
    }

    public EvaluatorOne categorySolicitation(String categorySolicitation) {
        this.categorySolicitation = categorySolicitation;
        return this;
    }

    public void setCategorySolicitation(String categorySolicitation) {
        this.categorySolicitation = categorySolicitation;
    }

    public String getStatus() {
        return status;
    }

    public EvaluatorOne status(String status) {
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
        EvaluatorOne evaluatorOne = (EvaluatorOne) o;
        if (evaluatorOne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluatorOne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvaluatorOne{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", solicitationId=" + getSolicitationId() +
            ", docURL='" + getDocURL() + "'" +
            ", categorySolicitation='" + getCategorySolicitation() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
