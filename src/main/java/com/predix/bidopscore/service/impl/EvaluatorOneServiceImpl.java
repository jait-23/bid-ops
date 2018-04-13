package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.EvaluatorOneService;
import com.predix.bidopscore.domain.EvaluatorOne;
import com.predix.bidopscore.repository.EvaluatorOneRepository;
import com.predix.bidopscore.service.dto.EvaluatorOneDTO;
import com.predix.bidopscore.service.mapper.EvaluatorOneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EvaluatorOne.
 */
@Service
@Transactional
public class EvaluatorOneServiceImpl implements EvaluatorOneService {

    private final Logger log = LoggerFactory.getLogger(EvaluatorOneServiceImpl.class);

    private final EvaluatorOneRepository evaluatorOneRepository;

    private final EvaluatorOneMapper evaluatorOneMapper;

    public EvaluatorOneServiceImpl(EvaluatorOneRepository evaluatorOneRepository, EvaluatorOneMapper evaluatorOneMapper) {
        this.evaluatorOneRepository = evaluatorOneRepository;
        this.evaluatorOneMapper = evaluatorOneMapper;
    }

    /**
     * Save a evaluatorOne.
     *
     * @param evaluatorOneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EvaluatorOneDTO save(EvaluatorOneDTO evaluatorOneDTO) {
        log.debug("Request to save EvaluatorOne : {}", evaluatorOneDTO);
        EvaluatorOne evaluatorOne = evaluatorOneMapper.toEntity(evaluatorOneDTO);
        evaluatorOne = evaluatorOneRepository.save(evaluatorOne);
        return evaluatorOneMapper.toDto(evaluatorOne);
    }

    /**
     * Get all the evaluatorOnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EvaluatorOneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluatorOnes");
        return evaluatorOneRepository.findAll(pageable)
            .map(evaluatorOneMapper::toDto);
    }

    /**
     * Get one evaluatorOne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EvaluatorOneDTO findOne(Long id) {
        log.debug("Request to get EvaluatorOne : {}", id);
        EvaluatorOne evaluatorOne = evaluatorOneRepository.findOne(id);
        return evaluatorOneMapper.toDto(evaluatorOne);
    }

    /**
     * Delete the evaluatorOne by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluatorOne : {}", id);
        evaluatorOneRepository.delete(id);
    }
}
