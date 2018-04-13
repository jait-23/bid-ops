package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.EvaluatorOneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EvaluatorOne and its DTO EvaluatorOneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvaluatorOneMapper extends EntityMapper<EvaluatorOneDTO, EvaluatorOne> {



    default EvaluatorOne fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvaluatorOne evaluatorOne = new EvaluatorOne();
        evaluatorOne.setId(id);
        return evaluatorOne;
    }
}
