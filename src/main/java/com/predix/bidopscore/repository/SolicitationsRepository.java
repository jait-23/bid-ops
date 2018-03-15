package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.Solicitations;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Solicitations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitationsRepository extends JpaRepository<Solicitations, Long> {

}
