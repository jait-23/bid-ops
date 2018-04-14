package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.BiddersSolicitationsSubmittedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BiddersSolicitationsSubmitted and its DTO BiddersSolicitationsSubmittedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BiddersSolicitationsSubmittedMapper extends EntityMapper<BiddersSolicitationsSubmittedDTO, BiddersSolicitationsSubmitted> {



    default BiddersSolicitationsSubmitted fromId(Long id) {
        if (id == null) {
            return null;
        }
        BiddersSolicitationsSubmitted biddersSolicitationsSubmitted = new BiddersSolicitationsSubmitted();
        biddersSolicitationsSubmitted.setId(id);
        return biddersSolicitationsSubmitted;
    }
}