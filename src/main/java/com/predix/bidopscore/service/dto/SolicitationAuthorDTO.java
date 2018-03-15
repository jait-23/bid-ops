package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SolicitationAuthor entity.
 */
public class SolicitationAuthorDTO implements Serializable {

    private Long id;

    private Long authorId;

    private Long solicitationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

        SolicitationAuthorDTO solicitationAuthorDTO = (SolicitationAuthorDTO) o;
        if(solicitationAuthorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitationAuthorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitationAuthorDTO{" +
            "id=" + getId() +
            ", authorId=" + getAuthorId() +
            ", solicitationId=" + getSolicitationId() +
            "}";
    }
}
