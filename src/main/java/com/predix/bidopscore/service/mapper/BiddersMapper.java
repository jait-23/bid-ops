package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.BiddersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bidders and its DTO BiddersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BiddersMapper extends EntityMapper<BiddersDTO, Bidders> {



    default Bidders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bidders bidders = new Bidders();
        bidders.setId(id);
        return bidders;
    }
}
