/*package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.BiddersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bidders and its DTO BiddersDTO.
 */
/*
@Mapper(componentModel = "spring", uses = {User.class, SolicitationsMapper.class})
public interface BiddersMapper extends EntityMapper<BiddersDTO, Bidders> {

    @Mapping(source = "jhi_user.id", target = "jhi_userId")
    @Mapping(source = "jhi_user.login", target = "jhi_userLogin")
    @Mapping(source = "solicitationId", target = "solicitations")
    BiddersDTO toDto(Bidders bidders);

    @Mapping(target = "bidders", ignore = true)
    @Mapping(source = "jhi_userId", target = "jhi_user")
    @Mapping(source = "solicitationsId", target = "solicitations")
    Bidders toEntity(BiddersDTO biddersDTO);

    default Bidders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bidders bidders = new Bidders();
        bidders.setId(id);
        return bidders;
    }
}


*/

package com.predix.bidopscore.service.mapper;

import com.predix.bidopscore.domain.*;
import com.predix.bidopscore.service.dto.BiddersDTO;

import java.util.List;

import org.mapstruct.*;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Bidders and its DTO BiddersDTO.
 */

@Mapper(componentModel = "spring", uses = {User.class, SolicitationsMapper.class})
@Component
public interface BiddersMapper {

	BiddersDTO biddersToBiddersDTO(Bidders bidders);
	BiddersDTO toDto(Bidders bidders);
	
    @Mapping(source = "jhi_user.id", target = "jhi_userId")
    @Mapping(source = "solicitationId", target = "solicitations")

    @Mapping(target = "bidders", ignore = true)
    @Mapping(source = "jhi_userId", target = "jhi_user")
    Bidders biddersDTOToBidders(BiddersDTO biddersDTO);

    default Bidders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bidders bidders = new Bidders();
        bidders.setId(id);
        return bidders;
    }
    
    default Solicitations  solicitationsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Solicitations solicitations = new Solicitations();
        solicitations.setId(id);
        return solicitations;
    }

    Bidders toEntity(BiddersDTO biddersDTO);
}

