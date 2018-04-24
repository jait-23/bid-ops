package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.PrimaryEvaluations;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrimaryEvaluations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimaryEvaluationsRepository extends JpaRepository<PrimaryEvaluations, Long> {

}
