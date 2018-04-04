package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.BiddersService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.BiddersDTO;
import com.predix.bidopscore.service.dto.BiddersCriteria;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Bidders.
 */
@RestController
@RequestMapping("/api")
public class BiddersResource {

    private final Logger log = LoggerFactory.getLogger(BiddersResource.class);

    private static final String ENTITY_NAME = "bidders";

    private final BiddersService biddersService;

    public BiddersResource(BiddersService biddersService) {
        this.biddersService = biddersService;
    }

    /**
     * POST  /bidders : Create a new bidders.
     *
     * @param biddersDTO the biddersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new biddersDTO, or with status 400 (Bad Request) if the bidders has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bidders")
    @Timed
    public ResponseEntity<BiddersDTO> createBidders(@RequestBody BiddersDTO biddersDTO) throws URISyntaxException {
        log.debug("REST request to save Bidders : {}", biddersDTO);
        if (biddersDTO.getId() != null) {
            throw new BadRequestAlertException("A new bidders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiddersDTO result = biddersService.save(biddersDTO);
        return ResponseEntity.created(new URI("/api/bidders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bidders : Updates an existing bidders.
     *
     * @param biddersDTO the biddersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated biddersDTO,
     * or with status 400 (Bad Request) if the biddersDTO is not valid,
     * or with status 500 (Internal Server Error) if the biddersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bidders")
    @Timed
    public ResponseEntity<BiddersDTO> updateBidders(@RequestBody BiddersDTO biddersDTO) throws URISyntaxException {
        log.debug("REST request to update Bidders : {}", biddersDTO);
        if (biddersDTO.getId() == null) {
            return createBidders(biddersDTO);
        }
        BiddersDTO result = biddersService.save(biddersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, biddersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bidders : get all the bidders.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of bidders in body
     */
    @GetMapping("/bidders")
    @Timed
    public ResponseEntity<List<BiddersDTO>> getAllBidders(Pageable pageable) {
        log.debug("REST request to get Bidders: {}");
        Page<BiddersDTO> page = biddersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bidders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bidders/:id : get the "id" bidders.
     *
     * @param id the id of the biddersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the biddersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bidders/{id}")
    @Timed
    public ResponseEntity<BiddersDTO> getBidders(@PathVariable Long id) {
        log.debug("REST request to get Bidders : {}", id);
        BiddersDTO biddersDTO = biddersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(biddersDTO));
    }

    /**
     * DELETE  /bidders/:id : delete the "id" bidders.
     *
     * @param id the id of the biddersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bidders/{id}")
    @Timed
    public ResponseEntity<Void> deleteBidders(@PathVariable Long id) {
        log.debug("REST request to delete Bidders : {}", id);
        biddersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
