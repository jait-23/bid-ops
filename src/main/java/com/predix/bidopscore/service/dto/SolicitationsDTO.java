package com.predix.bidopscore.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.predix.bidopscore.service.dto.FilesDTO;

/**
 * A DTO for the Solicitations entity.
 */
public class SolicitationsDTO implements Serializable {

    private Long id;

    private String solicitationId;

    private String title;

    private String status;

    private ZonedDateTime finalFilingDate;

    private ZonedDateTime lastUpdated;

    private String type;

    private String description;

    private String category;

    private String requiredDocuments;

    private String reviewerDeliveryStatus;

    private String approverStatus;

    private Long authorId;

    private Long userId;
    
    private Set<FilesDTO> filesDTOs = new HashSet<>();
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(String solicitationId) {
        this.solicitationId = solicitationId;
    }
    
    public Set<FilesDTO> getFilesDTOs() {
		return filesDTOs;
	}

	public void setFilesDTOs(Set<FilesDTO> filesDTOs) {
		this.filesDTOs = filesDTOs;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getFinalFilingDate() {
        return finalFilingDate;
    }

    public void setFinalFilingDate(ZonedDateTime finalFilingDate) {
        this.finalFilingDate = finalFilingDate;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(String requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public String getReviewerDeliveryStatus() {
        return reviewerDeliveryStatus;
    }

    public void setReviewerDeliveryStatus(String reviewerDeliveryStatus) {
        this.reviewerDeliveryStatus = reviewerDeliveryStatus;
    }

    public String getApproverStatus() {
        return approverStatus;
    }

    public void setApproverStatus(String approverStatus) {
        this.approverStatus = approverStatus;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolicitationsDTO solicitationsDTO = (SolicitationsDTO) o;
        if(solicitationsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitationsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitationsDTO{" +
            "id=" + getId() +
            ", solicitationId=" + getSolicitationId() +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", finalFilingDate='" + getFinalFilingDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", filesDTOs=" + filesDTOs + "'" +
            ", requiredDocuments='" + getRequiredDocuments() + "'" +
            ", reviewerDeliveryStatus='" + getReviewerDeliveryStatus() + "'" +
            ", approverStatus='" + getApproverStatus() + "'" +
            ", authorId=" + getAuthorId() +
            ", userId=" + getUserId() +
            "}";
    }
}