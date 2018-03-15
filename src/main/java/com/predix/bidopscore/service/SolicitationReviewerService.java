package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.SolicitationReviewerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SolicitationReviewer.
 */
public interface SolicitationReviewerService {

    /**
     * Save a solicitationReviewer.
     *
     * @param solicitationReviewerDTO the entity to save
     * @return the persisted entity
     */
    SolicitationReviewerDTO save(SolicitationReviewerDTO solicitationReviewerDTO);

    /**
     * Get all the solicitationReviewers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SolicitationReviewerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" solicitationReviewer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SolicitationReviewerDTO findOne(Long id);

    /**
     * Delete the "id" solicitationReviewer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
