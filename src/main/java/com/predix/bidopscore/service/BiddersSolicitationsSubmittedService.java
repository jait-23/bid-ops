package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.BiddersSolicitationsSubmittedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BiddersSolicitationsSubmitted.
 */
public interface BiddersSolicitationsSubmittedService {

    /**
     * Save a biddersSolicitationsSubmitted.
     *
     * @param biddersSolicitationsSubmittedDTO the entity to save
     * @return the persisted entity
     */
    BiddersSolicitationsSubmittedDTO save(BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO);

    /**
     * Get all the biddersSolicitationsSubmitted.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BiddersSolicitationsSubmittedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" biddersSolicitationsSubmitted.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BiddersSolicitationsSubmittedDTO findOne(Long id);

    /**
     * Delete the "id" biddersSolicitationsSubmitted.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
