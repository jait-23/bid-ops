package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.FilesDTO;
import com.predix.bidopscore.service.dto.SolicitationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Files and its DTO FilesDTO.
 */
@Mapper(componentModel = "spring", uses = {SolicitationsMapper.class})
public interface FilesMapper extends EntityMapper<FilesDTO, Files> {
	
	@Mapping(source = "solicitations", target = "solicitationsDTOs")
	public abstract FilesDTO filesToFilesDTO(Files files);
	
    FilesDTO toDto(Files files);

    @Mapping(source = "solicitationsDTOs", target = "solicitations")
    public abstract Files filesDTOToFiles(FilesDTO filesDTO);
    
    Files toEntity(FilesDTO filesDTO);

    default Files fromId(Long id) {
        if (id == null) {
            return null;
        }
        Files files = new Files();
        files.setId(id);
        return files;
    }
}
