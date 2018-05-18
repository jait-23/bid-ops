package com.predix.bidopscore.repository;

import com.predix.bidopscore.domain.Files;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Files entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilesRepository extends JpaRepository<Files, Long>, JpaSpecificationExecutor<Files> {

}
