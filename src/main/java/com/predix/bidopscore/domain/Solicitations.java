package com.predix.bidopscore.domain;


import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.predix.bidopscore.domain.Solicitations;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Solicitations.
 */
@Entity
@Table(name = "solicitations")
public class Solicitations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitation_id")
    private Long solicitationId;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;

    @Column(name = "final_filing_date")
    private ZonedDateTime finalFilingDate;

    @Column(name = "last_updated")
    private ZonedDateTime lastUpdated;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "required_documents")
    private String requiredDocuments;

    @Column(name = "reviewer_delivery_status")
    private String reviewerDeliveryStatus;

    @Column(name = "approver_status")
    private String approverStatus;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public Solicitations solicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
        return this;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }

    public String getTitle() {
        return title;
    }

    public Solicitations title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public Solicitations status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getFinalFilingDate() {
        return finalFilingDate;
    }

    public Solicitations finalFilingDate(ZonedDateTime finalFilingDate) {
        this.finalFilingDate = finalFilingDate;
        return this;
    }

    public void setFinalFilingDate(ZonedDateTime finalFilingDate) {
        this.finalFilingDate = finalFilingDate;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public Solicitations lastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getType() {
        return type;
    }

    public Solicitations type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Solicitations description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public Solicitations category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequiredDocuments() {
        return requiredDocuments;
    }

    public Solicitations requiredDocuments(String requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
        return this;
    }

    public void setRequiredDocuments(String requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public String getReviewerDeliveryStatus() {
        return reviewerDeliveryStatus;
    }

    public Solicitations reviewerDeliveryStatus(String reviewerDeliveryStatus) {
        this.reviewerDeliveryStatus = reviewerDeliveryStatus;
        return this;
    }

    public void setReviewerDeliveryStatus(String reviewerDeliveryStatus) {
        this.reviewerDeliveryStatus = reviewerDeliveryStatus;
    }

    public String getApproverStatus() {
        return approverStatus;
    }

    public Solicitations approverStatus(String approverStatus) {
        this.approverStatus = approverStatus;
        return this;
    }

    public void setApproverStatus(String approverStatus) {
        this.approverStatus = approverStatus;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Solicitations authorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public Solicitations reviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
        return this;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
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
        Solicitations solicitations = (Solicitations) o;
        if (solicitations.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitations.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Solicitations{" +
            "id=" + getId() +
            ", solicitationId=" + getSolicitationId() +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", finalFilingDate='" + getFinalFilingDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", requiredDocuments='" + getRequiredDocuments() + "'" +
            ", reviewerDeliveryStatus='" + getReviewerDeliveryStatus() + "'" +
            ", approverStatus='" + getApproverStatus() + "'" +
            ", authorId=" + getAuthorId() +
            ", reviewerId=" + getReviewerId() +
            "}";
    }
}