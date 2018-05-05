package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.SecondaryEvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SecondaryEvaluation and its DTO SecondaryEvaluationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SecondaryEvaluationMapper extends EntityMapper<SecondaryEvaluationDTO, SecondaryEvaluation> {



    default SecondaryEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        SecondaryEvaluation secondaryEvaluation = new SecondaryEvaluation();
        secondaryEvaluation.setId(id);
        return secondaryEvaluation;
    }
}
