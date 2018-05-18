package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.SolicitationsDTO;
import com.predix.bidopscore.service.dto.FilesDTO;
import com.predix.bidopscore.service.mapper.FilesMapper;
import com.predix.bidopscore.domain.Files;
import com.predix.bidopscore.domain.Solicitations;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity Solicitations and its DTO SolicitationsDTO.
 */
@Mapper(componentModel = "spring", uses = {FilesMapper.class})
public interface SolicitationsMapper extends EntityMapper<SolicitationsDTO, Solicitations> {
	
	// @Mapping(source = "userId", target = "user")
	
	@Mapping(source = "files", target = "filesDTOs")
	public abstract SolicitationsDTO solicitationsToSolicitationsDTO(Solicitations solicitations);
	
	SolicitationsDTO toDto(Solicitations solicitations);

	@Mapping(source = "filesDTOs", target = "files")
	public abstract Solicitations solicitationsDTOToSolicitations(SolicitationsDTO solicitationsDTO);  
	
	Solicitations toEntity(SolicitationsDTO solicitationsDTO);
	
    default Solicitations fromId(Long id) {
        if (id == null) {
            return null;
        }
        Solicitations solicitations = new Solicitations();
        solicitations.setId(id);
        return solicitations;
    }
}