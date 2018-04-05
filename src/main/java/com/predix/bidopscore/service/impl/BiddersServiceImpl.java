package com.predix.bidopscore.service.impl;

import com.predix.bidopscore.service.BiddersService;
import com.predix.bidopscore.domain.Bidders;
import com.predix.bidopscore.repository.BiddersRepository;
import com.predix.bidopscore.service.dto.BiddersDTO;
import com.predix.bidopscore.service.mapper.BiddersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bidders.
 */
@Service
@Transactional
public class BiddersServiceImpl implements BiddersService {

    private final Logger log = LoggerFactory.getLogger(BiddersServiceImpl.class);

    private final BiddersRepository biddersRepository;

    private final BiddersMapper biddersMapper;

    public BiddersServiceImpl(BiddersRepository biddersRepository, BiddersMapper biddersMapper) {
        this.biddersRepository = biddersRepository;
        this.biddersMapper = biddersMapper;
    }

    /**
     * Save a bidders.
     *
     * @param biddersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BiddersDTO save(BiddersDTO biddersDTO) {
        log.debug("Request to save Bidders : {}", biddersDTO);
        Bidders bidders = biddersMapper.toEntity(biddersDTO);
        bidders = biddersRepository.save(bidders);
        return biddersMapper.toDto(bidders);
    }

    /**
     * Get all the bidders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BiddersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bidders");
        return biddersRepository.findAll(pageable)
            .map(biddersMapper::toDto);
    }

    /**
     * Get one bidders by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BiddersDTO findOne(Long id) {
        log.debug("Request to get Bidders : {}", id);
        Bidders bidders = biddersRepository.findOne(id);
        return biddersMapper.toDto(bidders);
    }

    /**
     * Delete the bidders by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bidders : {}", id);
        biddersRepository.delete(id);
    }
}
