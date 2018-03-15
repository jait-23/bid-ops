package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.SolicitationsService;
import com.predix.bidopscore.domain.Solicitations;
import com.predix.bidopscore.repository.SolicitationsRepository;
import com.predix.bidopscore.service.dto.SolicitationsDTO;
import com.predix.bidopscore.service.mapper.SolicitationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Solicitations.
 */
@Service
@Transactional
public class SolicitationsServiceImpl implements SolicitationsService {

    private final Logger log = LoggerFactory.getLogger(SolicitationsServiceImpl.class);

    private final SolicitationsRepository solicitationsRepository;

    private final SolicitationsMapper solicitationsMapper;

    public SolicitationsServiceImpl(SolicitationsRepository solicitationsRepository, SolicitationsMapper solicitationsMapper) {
        this.solicitationsRepository = solicitationsRepository;
        this.solicitationsMapper = solicitationsMapper;
    }

    /**
     * Save a solicitations.
     *
     * @param solicitationsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SolicitationsDTO save(SolicitationsDTO solicitationsDTO) {
        log.debug("Request to save Solicitations : {}", solicitationsDTO);
        Solicitations solicitations = solicitationsMapper.toEntity(solicitationsDTO);
        solicitations = solicitationsRepository.save(solicitations);
        return solicitationsMapper.toDto(solicitations);
    }

    /**
     * Get all the solicitations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SolicitationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Solicitations");
        return solicitationsRepository.findAll(pageable)
            .map(solicitationsMapper::toDto);
    }

    /**
     * Get one solicitations by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SolicitationsDTO findOne(Long id) {
        log.debug("Request to get Solicitations : {}", id);
        Solicitations solicitations = solicitationsRepository.findOne(id);
        return solicitationsMapper.toDto(solicitations);
    }

    /**
     * Delete the solicitations by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Solicitations : {}", id);
        solicitationsRepository.delete(id);
    }
}
