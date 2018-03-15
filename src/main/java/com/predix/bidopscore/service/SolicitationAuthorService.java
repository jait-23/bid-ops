package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.SolicitationAuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SolicitationAuthor.
 */
public interface SolicitationAuthorService {

    /**
     * Save a solicitationAuthor.
     *
     * @param solicitationAuthorDTO the entity to save
     * @return the persisted entity
     */
    SolicitationAuthorDTO save(SolicitationAuthorDTO solicitationAuthorDTO);

    /**
     * Get all the solicitationAuthors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SolicitationAuthorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" solicitationAuthor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SolicitationAuthorDTO findOne(Long id);

    /**
     * Delete the "id" solicitationAuthor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
