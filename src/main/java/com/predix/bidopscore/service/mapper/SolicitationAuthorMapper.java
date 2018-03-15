package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.SolicitationAuthorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SolicitationAuthor and its DTO SolicitationAuthorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolicitationAuthorMapper extends EntityMapper<SolicitationAuthorDTO, SolicitationAuthor> {



    default SolicitationAuthor fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicitationAuthor solicitationAuthor = new SolicitationAuthor();
        solicitationAuthor.setId(id);
        return solicitationAuthor;
    }
}
