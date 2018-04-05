package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.BiddersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Bidders.
 */
public interface BiddersService {

    /**
     * Save a bidders.
     *
     * @param biddersDTO the entity to save
     * @return the persisted entity
     */
    BiddersDTO save(BiddersDTO biddersDTO);

    /**
     * Get all the bidders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BiddersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bidders.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BiddersDTO findOne(Long id);

    /**
     * Delete the "id" bidders.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
