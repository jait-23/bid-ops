package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.SecondaryEvaluation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SecondaryEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecondaryEvaluationRepository extends JpaRepository<SecondaryEvaluation, Long> {

}
