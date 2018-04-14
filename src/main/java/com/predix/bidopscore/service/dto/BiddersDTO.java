package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Bidders entity.
 */
public class BiddersDTO implements Serializable {

    private Long id;

    private String name;

    private Long solicitationWishlistId;

    private String subscribedCategories;

    private Long submittedSolicitationsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSolicitationWishlistId() {
        return solicitationWishlistId;
    }

    public void setSolicitationWishlistId(Long solicitationWishlistId) {
        this.solicitationWishlistId = solicitationWishlistId;
    }

    public String getSubscribedCategories() {
        return subscribedCategories;
    }

    public void setSubscribedCategories(String subscribedCategories) {
        this.subscribedCategories = subscribedCategories;
    }

    public Long getSubmittedSolicitationsId() {
        return submittedSolicitationsId;
    }

    public void setSubmittedSolicitationsId(Long submittedSolicitationsId) {
        this.submittedSolicitationsId = submittedSolicitationsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BiddersDTO biddersDTO = (BiddersDTO) o;
        if(biddersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), biddersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BiddersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", solicitationWishlistId=" + getSolicitationWishlistId() +
            ", subscribedCategories='" + getSubscribedCategories() + "'" +
            ", submittedSolicitationsId=" + getSubmittedSolicitationsId() +
            "}";
    }
}