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

    @Column(name = "solicitation_wishlist_id")
    private Long solicitationWishlistId;

    @Column(name = "subscribed_categories")
    private String subscribedCategories;

    @Column(name = "submitted_solicitations_id")
    private Long submittedSolicitationsId;

    @Column(name = "proposed_fee")
    private Integer proposedFee;

    @Column(name = "minimum_score_for_eligibility")
    private Integer minimumScoreForEligibility;

    @Column(name = "maximum_fee_score")
    private Integer maximumFeeScore;

    @Column(name = "fee_score")
    private Integer feeScore;

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

    public Long getSolicitationWishlistId() {
        return solicitationWishlistId;
    }

    public Bidders solicitationWishlistId(Long solicitationWishlistId) {
        this.solicitationWishlistId = solicitationWishlistId;
        return this;
    }

    public void setSolicitationWishlistId(Long solicitationWishlistId) {
        this.solicitationWishlistId = solicitationWishlistId;
    }

    public String getSubscribedCategories() {
        return subscribedCategories;
    }

    public Bidders subscribedCategories(String subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
        return this;
    }

    public void setSubscribedCategories(String subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
    }

    public Long getSubmittedSolicitationsId() {
        return submittedSolicitationsId;
    }

    public Bidders submittedSolicitationsId(Long submittedSolicitationsId) {
        this.submittedSolicitationsId = submittedSolicitationsId;
        return this;
    }

    public void setSubmittedSolicitationsId(Long submittedSolicitationsId) {
        this.submittedSolicitationsId = submittedSolicitationsId;
    }

    public Integer getProposedFee() {
        return proposedFee;
    }

    public Bidders proposedFee(Integer proposedFee) {
        this.proposedFee = proposedFee;
        return this;
    }

    public void setProposedFee(Integer proposedFee) {
        this.proposedFee = proposedFee;
    }

    public Integer getMinimumScoreForEligibility() {
        return minimumScoreForEligibility;
    }

    public Bidders minimumScoreForEligibility(Integer minimumScoreForEligibility) {
        this.minimumScoreForEligibility = minimumScoreForEligibility;
        return this;
    }

    public void setMinimumScoreForEligibility(Integer minimumScoreForEligibility) {
        this.minimumScoreForEligibility = minimumScoreForEligibility;
    }

    public Integer getMaximumFeeScore() {
        return maximumFeeScore;
    }

    public Bidders maximumFeeScore(Integer maximumFeeScore) {
        this.maximumFeeScore = maximumFeeScore;
        return this;
    }

    public void setMaximumFeeScore(Integer maximumFeeScore) {
        this.maximumFeeScore = maximumFeeScore;
    }

    public Integer getFeeScore() {
        return feeScore;
    }

    public Bidders feeScore(Integer feeScore) {
        this.feeScore = feeScore;
        return this;
    }

    public void setFeeScore(Integer feeScore) {
        this.feeScore = feeScore;
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
            ", solicitationWishlistId=" + getSolicitationWishlistId() +
            ", subscribedCategories='" + getSubscribedCategories() + "'" +
            ", submittedSolicitationsId=" + getSubmittedSolicitationsId() +
            ", proposedFee=" + getProposedFee() +
            ", minimumScoreForEligibility=" + getMinimumScoreForEligibility() +
            ", maximumFeeScore=" + getMaximumFeeScore() +
            ", feeScore=" + getFeeScore() +
            "}";
    }
}
