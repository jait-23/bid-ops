package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.SolicitationAuthor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SolicitationAuthor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitationAuthorRepository extends JpaRepository<SolicitationAuthor, Long> {

}
