package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.PrimaryEvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrimaryEvaluation and its DTO PrimaryEvaluationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrimaryEvaluationMapper extends EntityMapper<PrimaryEvaluationDTO, PrimaryEvaluation> {



    default PrimaryEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrimaryEvaluation primaryEvaluation = new PrimaryEvaluation();
        primaryEvaluation.setId(id);
        return primaryEvaluation;
    }
}
