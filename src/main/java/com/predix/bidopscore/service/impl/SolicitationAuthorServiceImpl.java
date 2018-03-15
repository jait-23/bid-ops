package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.SolicitationAuthorService;
import com.predix.bidopscore.domain.SolicitationAuthor;
import com.predix.bidopscore.repository.SolicitationAuthorRepository;
import com.predix.bidopscore.service.dto.SolicitationAuthorDTO;
import com.predix.bidopscore.service.mapper.SolicitationAuthorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SolicitationAuthor.
 */
@Service
@Transactional
public class SolicitationAuthorServiceImpl implements SolicitationAuthorService {

    private final Logger log = LoggerFactory.getLogger(SolicitationAuthorServiceImpl.class);

    private final SolicitationAuthorRepository solicitationAuthorRepository;

    private final SolicitationAuthorMapper solicitationAuthorMapper;

    public SolicitationAuthorServiceImpl(SolicitationAuthorRepository solicitationAuthorRepository, SolicitationAuthorMapper solicitationAuthorMapper) {
        this.solicitationAuthorRepository = solicitationAuthorRepository;
        this.solicitationAuthorMapper = solicitationAuthorMapper;
    }

    /**
     * Save a solicitationAuthor.
     *
     * @param solicitationAuthorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SolicitationAuthorDTO save(SolicitationAuthorDTO solicitationAuthorDTO) {
        log.debug("Request to save SolicitationAuthor : {}", solicitationAuthorDTO);
        SolicitationAuthor solicitationAuthor = solicitationAuthorMapper.toEntity(solicitationAuthorDTO);
        solicitationAuthor = solicitationAuthorRepository.save(solicitationAuthor);
        return solicitationAuthorMapper.toDto(solicitationAuthor);
    }

    /**
     * Get all the solicitationAuthors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SolicitationAuthorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SolicitationAuthors");
        return solicitationAuthorRepository.findAll(pageable)
            .map(solicitationAuthorMapper::toDto);
    }

    /**
     * Get one solicitationAuthor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SolicitationAuthorDTO findOne(Long id) {
        log.debug("Request to get SolicitationAuthor : {}", id);
        SolicitationAuthor solicitationAuthor = solicitationAuthorRepository.findOne(id);
        return solicitationAuthorMapper.toDto(solicitationAuthor);
    }

    /**
     * Delete the solicitationAuthor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SolicitationAuthor : {}", id);
        solicitationAuthorRepository.delete(id);
    }
}
