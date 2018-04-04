/*package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.SolicitationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Solicitations and its DTO SolicitationsDTO.
 */
/*
@Mapper(componentModel = "spring", uses = {})
public interface SolicitationsMapper extends EntityMapper<SolicitationsDTO, Solicitations> {

	@Mapping(target = "bidders", ignore = true)

    default Solicitations fromId(Long id) {
        if (id == null) {
            return null;
        }
        Solicitations solicitations = new Solicitations();
        solicitations.setId(id);
        return solicitations;
    }
}

*/

package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.SolicitationsDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Solicitations and its DTO SolicitationsDTO.
 */

@Mapper(componentModel = "spring", uses = {})
@Component
public interface SolicitationsMapper { 
	
	SolicitationsDTO solicitationsToSolicitationsDTO(Solicitations solicitations);

	@Mapping(target = "bidders", ignore = true)

    default Solicitations fromId(Long id) {
        if (id == null) {
            return null;
        }
        Solicitations solicitations = new Solicitations();
        solicitations.setId(id);
        return solicitations;
    }
	
	Solicitations solicitationsDTOToSolicitations(SolicitationsDTO solicitationsDTO);
}
