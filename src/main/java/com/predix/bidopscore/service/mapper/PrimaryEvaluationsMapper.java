package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.PrimaryEvaluationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrimaryEvaluations and its DTO PrimaryEvaluationsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrimaryEvaluationsMapper extends EntityMapper<PrimaryEvaluationsDTO, PrimaryEvaluations> {



    default PrimaryEvaluations fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrimaryEvaluations primaryEvaluations = new PrimaryEvaluations();
        primaryEvaluations.setId(id);
        return primaryEvaluations;
    }
}
