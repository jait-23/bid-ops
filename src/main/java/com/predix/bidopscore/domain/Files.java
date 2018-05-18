package com.predix.bidopscore.domain;


import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.predix.bidopscore.domain.Solicitations;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Files.
 */
@Entity
@Table(name = "files")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

    @Column(name = "type")
    private String type;

    @Column(name = "document_name")
    private String documentName;

    @JsonBackReference(value = "files_id")
    @ManyToMany(mappedBy="files", cascade = CascadeType.REFRESH)
    private Set<Solicitations> solicitations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public Files content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public Files contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getType() {
        return type;
    }

    public Files type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocumentName() {
        return documentName;
    }

    public Files documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Set<Solicitations> getSolicitations() {
        return solicitations;
    }

    public void setSolicitations(Set<Solicitations> solicitations) {
        this.solicitations = solicitations;
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
        Files files = (Files) o;
        if (files.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), files.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Files{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", type='" + getType() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            "}";
    }
}
