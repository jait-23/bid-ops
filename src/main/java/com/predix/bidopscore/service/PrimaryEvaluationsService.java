package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.PrimaryEvaluationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PrimaryEvaluations.
 */
public interface PrimaryEvaluationsService {

    /**
     * Save a primaryEvaluations.
     *
     * @param primaryEvaluationsDTO the entity to save
     * @return the persisted entity
     */
    PrimaryEvaluationsDTO save(PrimaryEvaluationsDTO primaryEvaluationsDTO);

    /**
     * Get all the primaryEvaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PrimaryEvaluationsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" primaryEvaluations.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PrimaryEvaluationsDTO findOne(Long id);

    /**
     * Delete the "id" primaryEvaluations.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
