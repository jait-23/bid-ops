package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.BiddersSolicitationsSubmittedService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.BiddersSolicitationsSubmittedDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BiddersSolicitationsSubmitted.
 */
@RestController
@RequestMapping("/api")
public class BiddersSolicitationsSubmittedResource {

    private final Logger log = LoggerFactory.getLogger(BiddersSolicitationsSubmittedResource.class);

    private static final String ENTITY_NAME = "biddersSolicitationsSubmitted";

    private final BiddersSolicitationsSubmittedService biddersSolicitationsSubmittedService;

    public BiddersSolicitationsSubmittedResource(BiddersSolicitationsSubmittedService biddersSolicitationsSubmittedService) {
        this.biddersSolicitationsSubmittedService = biddersSolicitationsSubmittedService;
    }

    /**
     * POST  /bidders-solicitations-submitteds : Create a new biddersSolicitationsSubmitted.
     *
     * @param biddersSolicitationsSubmittedDTO the biddersSolicitationsSubmittedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new biddersSolicitationsSubmittedDTO, or with status 400 (Bad Request) if the biddersSolicitationsSubmitted has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bidders-solicitations-submitteds")
    @Timed
    public ResponseEntity<BiddersSolicitationsSubmittedDTO> createBiddersSolicitationsSubmitted(@RequestBody BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO) throws URISyntaxException {
        log.debug("REST request to save BiddersSolicitationsSubmitted : {}", biddersSolicitationsSubmittedDTO);
        if (biddersSolicitationsSubmittedDTO.getId() != null) {
            throw new BadRequestAlertException("A new biddersSolicitationsSubmitted cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiddersSolicitationsSubmittedDTO result = biddersSolicitationsSubmittedService.save(biddersSolicitationsSubmittedDTO);
        return ResponseEntity.created(new URI("/api/bidders-solicitations-submitteds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bidders-solicitations-submitteds : Updates an existing biddersSolicitationsSubmitted.
     *
     * @param biddersSolicitationsSubmittedDTO the biddersSolicitationsSubmittedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated biddersSolicitationsSubmittedDTO,
     * or with status 400 (Bad Request) if the biddersSolicitationsSubmittedDTO is not valid,
     * or with status 500 (Internal Server Error) if the biddersSolicitationsSubmittedDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bidders-solicitations-submitteds")
    @Timed
    public ResponseEntity<BiddersSolicitationsSubmittedDTO> updateBiddersSolicitationsSubmitted(@RequestBody BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO) throws URISyntaxException {
        log.debug("REST request to update BiddersSolicitationsSubmitted : {}", biddersSolicitationsSubmittedDTO);
        if (biddersSolicitationsSubmittedDTO.getId() == null) {
            return createBiddersSolicitationsSubmitted(biddersSolicitationsSubmittedDTO);
        }
        BiddersSolicitationsSubmittedDTO result = biddersSolicitationsSubmittedService.save(biddersSolicitationsSubmittedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, biddersSolicitationsSubmittedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bidders-solicitations-submitteds : get all the biddersSolicitationsSubmitteds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of biddersSolicitationsSubmitteds in body
     */
    @GetMapping("/bidders-solicitations-submitteds")
    @Timed
    public ResponseEntity<List<BiddersSolicitationsSubmittedDTO>> getAllBiddersSolicitationsSubmitteds(Pageable pageable) {
        log.debug("REST request to get a page of BiddersSolicitationsSubmitteds");
        Page<BiddersSolicitationsSubmittedDTO> page = biddersSolicitationsSubmittedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bidders-solicitations-submitteds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bidders-solicitations-submitteds/:id : get the "id" biddersSolicitationsSubmitted.
     *
     * @param id the id of the biddersSolicitationsSubmittedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the biddersSolicitationsSubmittedDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bidders-solicitations-submitteds/{id}")
    @Timed
    public ResponseEntity<BiddersSolicitationsSubmittedDTO> getBiddersSolicitationsSubmitted(@PathVariable Long id) {
        log.debug("REST request to get BiddersSolicitationsSubmitted : {}", id);
        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO = biddersSolicitationsSubmittedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(biddersSolicitationsSubmittedDTO));
    }

    /**
     * DELETE  /bidders-solicitations-submitteds/:id : delete the "id" biddersSolicitationsSubmitted.
     *
     * @param id the id of the biddersSolicitationsSubmittedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bidders-solicitations-submitteds/{id}")
    @Timed
    public ResponseEntity<Void> deleteBiddersSolicitationsSubmitted(@PathVariable Long id) {
        log.debug("REST request to delete BiddersSolicitationsSubmitted : {}", id);
        biddersSolicitationsSubmittedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}