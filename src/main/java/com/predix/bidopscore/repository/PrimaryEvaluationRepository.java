package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.PrimaryEvaluation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrimaryEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimaryEvaluationRepository extends JpaRepository<PrimaryEvaluation, Long> {

}
