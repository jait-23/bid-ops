package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EvaluatorOne entity.
 */
public class EvaluatorOneDTO implements Serializable {

    private Long id;

    private Long userId;

    private Long solicitationId;

    private String docURL;

    private String categorySolicitation;

    private String status;

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

    public String getCategorySolicitation() {
        return categorySolicitation;
    }

    public void setCategorySolicitation(String categorySolicitation) {
        this.categorySolicitation = categorySolicitation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EvaluatorOneDTO evaluatorOneDTO = (EvaluatorOneDTO) o;
        if(evaluatorOneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluatorOneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvaluatorOneDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", solicitationId=" + getSolicitationId() +
            ", docURL='" + getDocURL() + "'" +
            ", categorySolicitation='" + getCategorySolicitation() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
