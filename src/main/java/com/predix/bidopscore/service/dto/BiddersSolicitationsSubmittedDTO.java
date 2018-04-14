package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BiddersSolicitationsSubmitted entity.
 */
public class BiddersSolicitationsSubmittedDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long solicitationId;

    private String docURL;

    private String categorySolicitations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }

    public String getDocURL() {
        return docURL;
    }

    public void setDocURL(String docURL) {
        this.docURL = docURL;
    }

    public String getCategorySolicitations() {
        return categorySolicitations;
    }

    public void setCategorySolicitations(String categorySolicitations) {
        this.categorySolicitations = categorySolicitations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO = (BiddersSolicitationsSubmittedDTO) o;
        if(biddersSolicitationsSubmittedDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), biddersSolicitationsSubmittedDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BiddersSolicitationsSubmittedDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", solicitationId=" + getSolicitationId() +
            ", docURL='" + getDocURL() + "'" +
            ", categorySolicitations='" + getCategorySolicitations() + "'" +
            "}";
    }
}