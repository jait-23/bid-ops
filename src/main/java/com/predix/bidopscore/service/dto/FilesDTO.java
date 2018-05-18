package com.predix.bidopscore.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

import com.predix.bidopscore.service.dto.SolicitationsDTO;

/**
 * A DTO for the Files entity.
 */
public class FilesDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] content;
    
    private String contentContentType;

    private String type;

    private String documentName;
    
    private Set<SolicitationsDTO> solicitationsDTOs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Set<SolicitationsDTO> getSolicitationsDTOs() {
		return solicitationsDTOs;
	}
    
    public void setSolicitationsDTOs(Set<SolicitationsDTO> solicitationsDTOs) {
		this.solicitationsDTOs = solicitationsDTOs;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilesDTO filesDTO = (FilesDTO) o;
        if(filesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilesDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", type='" + getType() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            "}";
    }

	public String getContentContentType() {
		return contentContentType;
	}

	public void setContentContentType(String contentContentType) {
		this.contentContentType = contentContentType;
	}
}
