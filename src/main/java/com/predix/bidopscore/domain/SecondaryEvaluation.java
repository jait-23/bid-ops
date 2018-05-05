package com.predix.bidopscore.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SecondaryEvaluation.
 */
@Entity
@Table(name = "secondary_evaluation")
public class SecondaryEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "evaluator_id")
    private Long evaluator_id;

    @Column(name = "doc_url")
    private String doc_url;

    @Column(name = "solicitation_id")
    private Long solicitation_id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "eligible")
    private String eligible;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public SecondaryEvaluation user_id(Long user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getEvaluator_id() {
        return evaluator_id;
    }

    public SecondaryEvaluation evaluator_id(Long evaluator_id) {
        this.evaluator_id = evaluator_id;
        return this;
    }

    public void setEvaluator_id(Long evaluator_id) {
        this.evaluator_id = evaluator_id;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public SecondaryEvaluation doc_url(String doc_url) {
        this.doc_url = doc_url;
        return this;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public Long getSolicitation_id() {
        return solicitation_id;
    }

    public SecondaryEvaluation solicitation_id(Long solicitation_id) {
        this.solicitation_id = solicitation_id;
        return this;
    }

    public void setSolicitation_id(Long solicitation_id) {
        this.solicitation_id = solicitation_id;
    }

    public Integer getScore() {
        return score;
    }

    public SecondaryEvaluation score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getEligible() {
        return eligible;
    }

    public SecondaryEvaluation eligible(String eligible) {
        this.eligible = eligible;
        return this;
    }

    public void setEligible(String eligible) {
        this.eligible = eligible;
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
        SecondaryEvaluation secondaryEvaluation = (SecondaryEvaluation) o;
        if (secondaryEvaluation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secondaryEvaluation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SecondaryEvaluation{" +
            "id=" + getId() +
            ", user_id=" + getUser_id() +
            ", evaluator_id=" + getEvaluator_id() +
            ", doc_url='" + getDoc_url() + "'" +
            ", solicitation_id=" + getSolicitation_id() +
            ", score=" + getScore() +
            ", eligible='" + getEligible() + "'" +
            "}";
    }
}
