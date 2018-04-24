package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.PrimaryEvaluationsService;
import com.predix.bidopscore.domain.PrimaryEvaluations;
import com.predix.bidopscore.repository.PrimaryEvaluationsRepository;
import com.predix.bidopscore.service.dto.PrimaryEvaluationsDTO;
import com.predix.bidopscore.service.mapper.PrimaryEvaluationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PrimaryEvaluations.
 */
@Service
@Transactional
public class PrimaryEvaluationsServiceImpl implements PrimaryEvaluationsService {

    private final Logger log = LoggerFactory.getLogger(PrimaryEvaluationsServiceImpl.class);

    private final PrimaryEvaluationsRepository primaryEvaluationsRepository;

    private final PrimaryEvaluationsMapper primaryEvaluationsMapper;

    public PrimaryEvaluationsServiceImpl(PrimaryEvaluationsRepository primaryEvaluationsRepository, PrimaryEvaluationsMapper primaryEvaluationsMapper) {
        this.primaryEvaluationsRepository = primaryEvaluationsRepository;
        this.primaryEvaluationsMapper = primaryEvaluationsMapper;
    }

    /**
     * Save a primaryEvaluations.
     *
     * @param primaryEvaluationsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PrimaryEvaluationsDTO save(PrimaryEvaluationsDTO primaryEvaluationsDTO) {
        log.debug("Request to save PrimaryEvaluations : {}", primaryEvaluationsDTO);
        PrimaryEvaluations primaryEvaluations = primaryEvaluationsMapper.toEntity(primaryEvaluationsDTO);
        primaryEvaluations = primaryEvaluationsRepository.save(primaryEvaluations);
        return primaryEvaluationsMapper.toDto(primaryEvaluations);
    }

    /**
     * Get all the primaryEvaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrimaryEvaluationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrimaryEvaluations");
        return primaryEvaluationsRepository.findAll(pageable)
            .map(primaryEvaluationsMapper::toDto);
    }

    /**
     * Get one primaryEvaluations by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PrimaryEvaluationsDTO findOne(Long id) {
        log.debug("Request to get PrimaryEvaluations : {}", id);
        PrimaryEvaluations primaryEvaluations = primaryEvaluationsRepository.findOne(id);
        return primaryEvaluationsMapper.toDto(primaryEvaluations);
    }

    /**
     * Delete the primaryEvaluations by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrimaryEvaluations : {}", id);
        primaryEvaluationsRepository.delete(id);
    }
}
