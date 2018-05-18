package com.predix.bidopscore.service;

import com.predix.bidopscore.service.dto.FilesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Files.
 */
public interface FilesService {

    /**
     * Save a files.
     *
     * @param filesDTO the entity to save
     * @return the persisted entity
     */
    FilesDTO save(FilesDTO filesDTO);

    /**
     * Get all the files.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FilesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" files.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FilesDTO findOne(Long id);

    /**
     * Delete the "id" files.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
