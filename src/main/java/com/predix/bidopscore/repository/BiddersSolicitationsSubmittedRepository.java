package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.BiddersSolicitationsSubmitted;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BiddersSolicitationsSubmitted entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiddersSolicitationsSubmittedRepository extends JpaRepository<BiddersSolicitationsSubmitted, Long> {

}
