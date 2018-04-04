package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.Bidders;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bidders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiddersRepository extends JpaRepository<Bidders, Long>, JpaSpecificationExecutor<Bidders> {

}
