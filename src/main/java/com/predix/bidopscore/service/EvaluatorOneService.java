package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.EvaluatorOneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EvaluatorOne.
 */
public interface EvaluatorOneService {

    /**
     * Save a evaluatorOne.
     *
     * @param evaluatorOneDTO the entity to save
     * @return the persisted entity
     */
    EvaluatorOneDTO save(EvaluatorOneDTO evaluatorOneDTO);

    /**
     * Get all the evaluatorOnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EvaluatorOneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" evaluatorOne.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EvaluatorOneDTO findOne(Long id);

    /**
     * Delete the "id" evaluatorOne.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
