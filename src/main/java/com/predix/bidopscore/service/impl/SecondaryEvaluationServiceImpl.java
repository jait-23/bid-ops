package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.SecondaryEvaluationService;
import com.predix.bidopscore.domain.SecondaryEvaluation;
import com.predix.bidopscore.repository.SecondaryEvaluationRepository;
import com.predix.bidopscore.service.dto.SecondaryEvaluationDTO;
import com.predix.bidopscore.service.mapper.SecondaryEvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SecondaryEvaluation.
 */
@Service
@Transactional
public class SecondaryEvaluationServiceImpl implements SecondaryEvaluationService {

    private final Logger log = LoggerFactory.getLogger(SecondaryEvaluationServiceImpl.class);

    private final SecondaryEvaluationRepository secondaryEvaluationRepository;

    private final SecondaryEvaluationMapper secondaryEvaluationMapper;

    public SecondaryEvaluationServiceImpl(SecondaryEvaluationRepository secondaryEvaluationRepository, SecondaryEvaluationMapper secondaryEvaluationMapper) {
        this.secondaryEvaluationRepository = secondaryEvaluationRepository;
        this.secondaryEvaluationMapper = secondaryEvaluationMapper;
    }

    /**
     * Save a secondaryEvaluation.
     *
     * @param secondaryEvaluationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SecondaryEvaluationDTO save(SecondaryEvaluationDTO secondaryEvaluationDTO) {
        log.debug("Request to save SecondaryEvaluation : {}", secondaryEvaluationDTO);
        SecondaryEvaluation secondaryEvaluation = secondaryEvaluationMapper.toEntity(secondaryEvaluationDTO);
        secondaryEvaluation = secondaryEvaluationRepository.save(secondaryEvaluation);
        return secondaryEvaluationMapper.toDto(secondaryEvaluation);
    }

    /**
     * Get all the secondaryEvaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SecondaryEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SecondaryEvaluations");
        return secondaryEvaluationRepository.findAll(pageable)
            .map(secondaryEvaluationMapper::toDto);
    }

    /**
     * Get one secondaryEvaluation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SecondaryEvaluationDTO findOne(Long id) {
        log.debug("Request to get SecondaryEvaluation : {}", id);
        SecondaryEvaluation secondaryEvaluation = secondaryEvaluationRepository.findOne(id);
        return secondaryEvaluationMapper.toDto(secondaryEvaluation);
    }

    /**
     * Delete the secondaryEvaluation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SecondaryEvaluation : {}", id);
        secondaryEvaluationRepository.delete(id);
    }
}
