package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.BiddersSolicitationsSubmittedService;
import com.predix.bidopscore.domain.BiddersSolicitationsSubmitted;
import com.predix.bidopscore.repository.BiddersSolicitationsSubmittedRepository;
import com.predix.bidopscore.service.dto.BiddersSolicitationsSubmittedDTO;
import com.predix.bidopscore.service.mapper.BiddersSolicitationsSubmittedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BiddersSolicitationsSubmitted.
 */
@Service
@Transactional
public class BiddersSolicitationsSubmittedServiceImpl implements BiddersSolicitationsSubmittedService {

    private final Logger log = LoggerFactory.getLogger(BiddersSolicitationsSubmittedServiceImpl.class);

    private final BiddersSolicitationsSubmittedRepository biddersSolicitationsSubmittedRepository;

    private final BiddersSolicitationsSubmittedMapper biddersSolicitationsSubmittedMapper;

    public BiddersSolicitationsSubmittedServiceImpl(BiddersSolicitationsSubmittedRepository biddersSolicitationsSubmittedRepository, BiddersSolicitationsSubmittedMapper biddersSolicitationsSubmittedMapper) {
        this.biddersSolicitationsSubmittedRepository = biddersSolicitationsSubmittedRepository;
        this.biddersSolicitationsSubmittedMapper = biddersSolicitationsSubmittedMapper;
    }

    /**
     * Save a biddersSolicitationsSubmitted.
     *
     * @param biddersSolicitationsSubmittedDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BiddersSolicitationsSubmittedDTO save(BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO) {
        log.debug("Request to save BiddersSolicitationsSubmitted : {}", biddersSolicitationsSubmittedDTO);
        BiddersSolicitationsSubmitted biddersSolicitationsSubmitted = biddersSolicitationsSubmittedMapper.toEntity(biddersSolicitationsSubmittedDTO);
        biddersSolicitationsSubmitted = biddersSolicitationsSubmittedRepository.save(biddersSolicitationsSubmitted);
        return biddersSolicitationsSubmittedMapper.toDto(biddersSolicitationsSubmitted);
    }

    /**
     * Get all the biddersSolicitationsSubmitteds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BiddersSolicitationsSubmittedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BiddersSolicitationsSubmitteds");
        return biddersSolicitationsSubmittedRepository.findAll(pageable)
            .map(biddersSolicitationsSubmittedMapper::toDto);
    }

    /**
     * Get one biddersSolicitationsSubmitted by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BiddersSolicitationsSubmittedDTO findOne(Long id) {
        log.debug("Request to get BiddersSolicitationsSubmitted : {}", id);
        BiddersSolicitationsSubmitted biddersSolicitationsSubmitted = biddersSolicitationsSubmittedRepository.findOne(id);
        return biddersSolicitationsSubmittedMapper.toDto(biddersSolicitationsSubmitted);
    }

    /**
     * Delete the biddersSolicitationsSubmitted by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BiddersSolicitationsSubmitted : {}", id);
        biddersSolicitationsSubmittedRepository.delete(id);
    }
}