package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.SolicitationReviewerService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.SolicitationReviewerDTO;
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
 * REST controller for managing SolicitationReviewer.
 */
@RestController
@RequestMapping("/api")
public class SolicitationReviewerResource {

    private final Logger log = LoggerFactory.getLogger(SolicitationReviewerResource.class);

    private static final String ENTITY_NAME = "solicitationReviewer";

    private final SolicitationReviewerService solicitationReviewerService;

    public SolicitationReviewerResource(SolicitationReviewerService solicitationReviewerService) {
        this.solicitationReviewerService = solicitationReviewerService;
    }

    /**
     * POST  /solicitation-reviewers : Create a new solicitationReviewer.
     *
     * @param solicitationReviewerDTO the solicitationReviewerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solicitationReviewerDTO, or with status 400 (Bad Request) if the solicitationReviewer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solicitation-reviewers")
    @Timed
    public ResponseEntity<SolicitationReviewerDTO> createSolicitationReviewer(@RequestBody SolicitationReviewerDTO solicitationReviewerDTO) throws URISyntaxException {
        log.debug("REST request to save SolicitationReviewer : {}", solicitationReviewerDTO);
        if (solicitationReviewerDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitationReviewer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitationReviewerDTO result = solicitationReviewerService.save(solicitationReviewerDTO);
        return ResponseEntity.created(new URI("/api/solicitation-reviewers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solicitation-reviewers : Updates an existing solicitationReviewer.
     *
     * @param solicitationReviewerDTO the solicitationReviewerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solicitationReviewerDTO,
     * or with status 400 (Bad Request) if the solicitationReviewerDTO is not valid,
     * or with status 500 (Internal Server Error) if the solicitationReviewerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solicitation-reviewers")
    @Timed
    public ResponseEntity<SolicitationReviewerDTO> updateSolicitationReviewer(@RequestBody SolicitationReviewerDTO solicitationReviewerDTO) throws URISyntaxException {
        log.debug("REST request to update SolicitationReviewer : {}", solicitationReviewerDTO);
        if (solicitationReviewerDTO.getId() == null) {
            return createSolicitationReviewer(solicitationReviewerDTO);
        }
        SolicitationReviewerDTO result = solicitationReviewerService.save(solicitationReviewerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solicitationReviewerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solicitation-reviewers : get all the solicitationReviewers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solicitationReviewers in body
     */
    @GetMapping("/solicitation-reviewers")
    @Timed
    public ResponseEntity<List<SolicitationReviewerDTO>> getAllSolicitationReviewers(Pageable pageable) {
        log.debug("REST request to get a page of SolicitationReviewers");
        Page<SolicitationReviewerDTO> page = solicitationReviewerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solicitation-reviewers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solicitation-reviewers/:id : get the "id" solicitationReviewer.
     *
     * @param id the id of the solicitationReviewerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solicitationReviewerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/solicitation-reviewers/{id}")
    @Timed
    public ResponseEntity<SolicitationReviewerDTO> getSolicitationReviewer(@PathVariable Long id) {
        log.debug("REST request to get SolicitationReviewer : {}", id);
        SolicitationReviewerDTO solicitationReviewerDTO = solicitationReviewerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solicitationReviewerDTO));
    }

    /**
     * DELETE  /solicitation-reviewers/:id : delete the "id" solicitationReviewer.
     *
     * @param id the id of the solicitationReviewerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solicitation-reviewers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolicitationReviewer(@PathVariable Long id) {
        log.debug("REST request to delete SolicitationReviewer : {}", id);
        solicitationReviewerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
