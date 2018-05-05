package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.SecondaryEvaluationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SecondaryEvaluation.
 */
public interface SecondaryEvaluationService {

    /**
     * Save a secondaryEvaluation.
     *
     * @param secondaryEvaluationDTO the entity to save
     * @return the persisted entity
     */
    SecondaryEvaluationDTO save(SecondaryEvaluationDTO secondaryEvaluationDTO);

    /**
     * Get all the secondaryEvaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SecondaryEvaluationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" secondaryEvaluation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SecondaryEvaluationDTO findOne(Long id);

    /**
     * Delete the "id" secondaryEvaluation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
