package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Bidders entity.
 */
public class BiddersDTO implements Serializable {

    private Long id;

    private Long solicitationId;

    private String name;

    private String bidCategories;

    private Long userId;

    private Long jhi_userId;

    private String jhi_userLogin;

    private Long solicitationsId;

    private String solicitationsSolicitation_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBidCategories() {
        return bidCategories;
    }

    public void setBidCategories(String bidCategories) {
        this.bidCategories = bidCategories;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJhi_userId() {
        return jhi_userId;
    }

    public void setJhi_userId(Long jhi_userId) {
        this.jhi_userId = jhi_userId;
    }

    public String getJhi_userLogin() {
        return jhi_userLogin;
    }

    public void setJhi_userLogin(String jhi_userLogin) {
        this.jhi_userLogin = jhi_userLogin;
    }

    public Long getSolicitationsId() {
        return solicitationsId;
    }

    public void setSolicitationsId(Long solicitationsId) {
        this.solicitationsId = solicitationsId;
    }

    public String getSolicitationsSolicitation_id() {
        return solicitationsSolicitation_id;
    }

    public void setSolicitationsSolicitation_id(String solicitationsSolicitation_id) {
        this.solicitationsSolicitation_id = solicitationsSolicitation_id;
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
            ", solicitationId=" + getSolicitationId() +
            ", name='" + getName() + "'" +
            ", bidCategories='" + getBidCategories() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
