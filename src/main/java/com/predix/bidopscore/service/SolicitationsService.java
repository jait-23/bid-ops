package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.SolicitationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Solicitations.
 */
public interface SolicitationsService {

    /**
     * Save a solicitations.
     *
     * @param solicitationsDTO the entity to save
     * @return the persisted entity
     */
    SolicitationsDTO save(SolicitationsDTO solicitationsDTO);

    /**
     * Get all the solicitations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SolicitationsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" solicitations.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SolicitationsDTO findOne(Long id);

    /**
     * Delete the "id" solicitations.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
