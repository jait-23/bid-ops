package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.SolicitationReviewerService;
import com.predix.bidopscore.domain.SolicitationReviewer;
import com.predix.bidopscore.repository.SolicitationReviewerRepository;
import com.predix.bidopscore.service.dto.SolicitationReviewerDTO;
import com.predix.bidopscore.service.mapper.SolicitationReviewerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SolicitationReviewer.
 */
@Service
@Transactional
public class SolicitationReviewerServiceImpl implements SolicitationReviewerService {

    private final Logger log = LoggerFactory.getLogger(SolicitationReviewerServiceImpl.class);

    private final SolicitationReviewerRepository solicitationReviewerRepository;

    private final SolicitationReviewerMapper solicitationReviewerMapper;

    public SolicitationReviewerServiceImpl(SolicitationReviewerRepository solicitationReviewerRepository, SolicitationReviewerMapper solicitationReviewerMapper) {
        this.solicitationReviewerRepository = solicitationReviewerRepository;
        this.solicitationReviewerMapper = solicitationReviewerMapper;
    }

    /**
     * Save a solicitationReviewer.
     *
     * @param solicitationReviewerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SolicitationReviewerDTO save(SolicitationReviewerDTO solicitationReviewerDTO) {
        log.debug("Request to save SolicitationReviewer : {}", solicitationReviewerDTO);
        SolicitationReviewer solicitationReviewer = solicitationReviewerMapper.toEntity(solicitationReviewerDTO);
        solicitationReviewer = solicitationReviewerRepository.save(solicitationReviewer);
        return solicitationReviewerMapper.toDto(solicitationReviewer);
    }

    /**
     * Get all the solicitationReviewers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SolicitationReviewerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SolicitationReviewers");
        return solicitationReviewerRepository.findAll(pageable)
            .map(solicitationReviewerMapper::toDto);
    }

    /**
     * Get one solicitationReviewer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SolicitationReviewerDTO findOne(Long id) {
        log.debug("Request to get SolicitationReviewer : {}", id);
        SolicitationReviewer solicitationReviewer = solicitationReviewerRepository.findOne(id);
        return solicitationReviewerMapper.toDto(solicitationReviewer);
    }

    /**
     * Delete the solicitationReviewer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SolicitationReviewer : {}", id);
        solicitationReviewerRepository.delete(id);
    }
}
