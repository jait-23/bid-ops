package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.SolicitationReviewerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SolicitationReviewer and its DTO SolicitationReviewerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolicitationReviewerMapper extends EntityMapper<SolicitationReviewerDTO, SolicitationReviewer> {



    default SolicitationReviewer fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicitationReviewer solicitationReviewer = new SolicitationReviewer();
        solicitationReviewer.setId(id);
        return solicitationReviewer;
    }
}
