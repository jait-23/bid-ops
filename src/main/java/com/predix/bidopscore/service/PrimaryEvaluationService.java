package com.predix.bidopscore.service;

import com.predix.bidopscore.domain.PrimaryEvaluation;
import com.predix.bidopscore.repository.PrimaryEvaluationRepository;
import com.predix.bidopscore.service.dto.PrimaryEvaluationDTO;
import com.predix.bidopscore.service.mapper.PrimaryEvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PrimaryEvaluation.
 */
@Service
@Transactional
public class PrimaryEvaluationService {

    private final Logger log = LoggerFactory.getLogger(PrimaryEvaluationService.class);

    private final PrimaryEvaluationRepository primaryEvaluationRepository;

    private final PrimaryEvaluationMapper primaryEvaluationMapper;

    public PrimaryEvaluationService(PrimaryEvaluationRepository primaryEvaluationRepository, PrimaryEvaluationMapper primaryEvaluationMapper) {
        this.primaryEvaluationRepository = primaryEvaluationRepository;
        this.primaryEvaluationMapper = primaryEvaluationMapper;
    }

    /**
     * Save a primaryEvaluation.
     *
     * @param primaryEvaluationDTO the entity to save
     * @return the persisted entity
     */
    public PrimaryEvaluationDTO save(PrimaryEvaluationDTO primaryEvaluationDTO) {
        log.debug("Request to save PrimaryEvaluation : {}", primaryEvaluationDTO);
        PrimaryEvaluation primaryEvaluation = primaryEvaluationMapper.toEntity(primaryEvaluationDTO);
        primaryEvaluation = primaryEvaluationRepository.save(primaryEvaluation);
        return primaryEvaluationMapper.toDto(primaryEvaluation);
    }

    /**
     * Get all the primaryEvaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PrimaryEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrimaryEvaluations");
        return primaryEvaluationRepository.findAll(pageable)
            .map(primaryEvaluationMapper::toDto);
    }

    /**
     * Get one primaryEvaluation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrimaryEvaluationDTO findOne(Long id) {
        log.debug("Request to get PrimaryEvaluation : {}", id);
        PrimaryEvaluation primaryEvaluation = primaryEvaluationRepository.findOne(id);
        return primaryEvaluationMapper.toDto(primaryEvaluation);
    }

    /**
     * Delete the primaryEvaluation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrimaryEvaluation : {}", id);
        primaryEvaluationRepository.delete(id);
    }
}
