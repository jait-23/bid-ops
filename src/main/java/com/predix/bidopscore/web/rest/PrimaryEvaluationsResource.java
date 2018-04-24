package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.PrimaryEvaluationsService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.PrimaryEvaluationsDTO;
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
 * REST controller for managing PrimaryEvaluations.
 */
@RestController
@RequestMapping("/api")
public class PrimaryEvaluationsResource {

    private final Logger log = LoggerFactory.getLogger(PrimaryEvaluationsResource.class);

    private static final String ENTITY_NAME = "primaryEvaluations";

    private final PrimaryEvaluationsService primaryEvaluationsService;

    public PrimaryEvaluationsResource(PrimaryEvaluationsService primaryEvaluationsService) {
        this.primaryEvaluationsService = primaryEvaluationsService;
    }

    /**
     * POST  /primary-evaluations : Create a new primaryEvaluations.
     *
     * @param primaryEvaluationsDTO the primaryEvaluationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new primaryEvaluationsDTO, or with status 400 (Bad Request) if the primaryEvaluations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/primary-evaluations")
    @Timed
    public ResponseEntity<PrimaryEvaluationsDTO> createPrimaryEvaluations(@RequestBody PrimaryEvaluationsDTO primaryEvaluationsDTO) throws URISyntaxException {
        log.debug("REST request to save PrimaryEvaluations : {}", primaryEvaluationsDTO);
        if (primaryEvaluationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new primaryEvaluations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrimaryEvaluationsDTO result = primaryEvaluationsService.save(primaryEvaluationsDTO);
        return ResponseEntity.created(new URI("/api/primary-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /primary-evaluations : Updates an existing primaryEvaluations.
     *
     * @param primaryEvaluationsDTO the primaryEvaluationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated primaryEvaluationsDTO,
     * or with status 400 (Bad Request) if the primaryEvaluationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the primaryEvaluationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/primary-evaluations")
    @Timed
    public ResponseEntity<PrimaryEvaluationsDTO> updatePrimaryEvaluations(@RequestBody PrimaryEvaluationsDTO primaryEvaluationsDTO) throws URISyntaxException {
        log.debug("REST request to update PrimaryEvaluations : {}", primaryEvaluationsDTO);
        if (primaryEvaluationsDTO.getId() == null) {
            return createPrimaryEvaluations(primaryEvaluationsDTO);
        }
        PrimaryEvaluationsDTO result = primaryEvaluationsService.save(primaryEvaluationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, primaryEvaluationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /primary-evaluations : get all the primaryEvaluations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of primaryEvaluations in body
     */
    @GetMapping("/primary-evaluations")
    @Timed
    public ResponseEntity<List<PrimaryEvaluationsDTO>> getAllPrimaryEvaluations(Pageable pageable) {
        log.debug("REST request to get a page of PrimaryEvaluations");
        Page<PrimaryEvaluationsDTO> page = primaryEvaluationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/primary-evaluations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /primary-evaluations/:id : get the "id" primaryEvaluations.
     *
     * @param id the id of the primaryEvaluationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the primaryEvaluationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/primary-evaluations/{id}")
    @Timed
    public ResponseEntity<PrimaryEvaluationsDTO> getPrimaryEvaluations(@PathVariable Long id) {
        log.debug("REST request to get PrimaryEvaluations : {}", id);
        PrimaryEvaluationsDTO primaryEvaluationsDTO = primaryEvaluationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(primaryEvaluationsDTO));
    }

    /**
     * DELETE  /primary-evaluations/:id : delete the "id" primaryEvaluations.
     *
     * @param id the id of the primaryEvaluationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/primary-evaluations/{id}")
    @Timed
    public ResponseEntity<Void> deletePrimaryEvaluations(@PathVariable Long id) {
        log.debug("REST request to delete PrimaryEvaluations : {}", id);
        primaryEvaluationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
