package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.SolicitationReviewer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SolicitationReviewer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitationReviewerRepository extends JpaRepository<SolicitationReviewer, Long> {

}
