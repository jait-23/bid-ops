package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.EvaluatorOne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EvaluatorOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluatorOneRepository extends JpaRepository<EvaluatorOne, Long> {

}
