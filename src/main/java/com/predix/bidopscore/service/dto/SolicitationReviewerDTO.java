package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SolicitationReviewer entity.
 */
public class SolicitationReviewerDTO implements Serializable {

    private Long id;

    private Long reviewerId;

    private Long solicitationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolicitationReviewerDTO solicitationReviewerDTO = (SolicitationReviewerDTO) o;
        if(solicitationReviewerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitationReviewerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitationReviewerDTO{" +
            "id=" + getId() +
            ", reviewerId=" + getReviewerId() +
            ", solicitationId=" + getSolicitationId() +
            "}";
    }
}
