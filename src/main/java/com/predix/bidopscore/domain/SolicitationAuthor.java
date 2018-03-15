package com.predix.bidopscore.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SolicitationAuthor.
 */
@Entity
@Table(name = "solicitation_author")
public class SolicitationAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "solicitation_id")
    private Long solicitationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public SolicitationAuthor authorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public SolicitationAuthor solicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
        return this;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
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
        SolicitationAuthor solicitationAuthor = (SolicitationAuthor) o;
        if (solicitationAuthor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitationAuthor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitationAuthor{" +
            "id=" + getId() +
            ", authorId=" + getAuthorId() +
            ", solicitationId=" + getSolicitationId() +
            "}";
    }
}
