package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.PrimaryEvaluationService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.PrimaryEvaluationDTO;
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
 * REST controller for managing PrimaryEvaluation.
 */
@RestController
@RequestMapping("/api")
public class PrimaryEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(PrimaryEvaluationResource.class);

    private static final String ENTITY_NAME = "primaryEvaluation";

    private final PrimaryEvaluationService primaryEvaluationService;

    public PrimaryEvaluationResource(PrimaryEvaluationService primaryEvaluationService) {
        this.primaryEvaluationService = primaryEvaluationService;
    }

    /**
     * POST  /primary-evaluations : Create a new primaryEvaluation.
     *
     * @param primaryEvaluationDTO the primaryEvaluationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new primaryEvaluationDTO, or with status 400 (Bad Request) if the primaryEvaluation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/primary-evaluations")
    @Timed
    public ResponseEntity<PrimaryEvaluationDTO> createPrimaryEvaluation(@RequestBody PrimaryEvaluationDTO primaryEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save PrimaryEvaluation : {}", primaryEvaluationDTO);
        if (primaryEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new primaryEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrimaryEvaluationDTO result = primaryEvaluationService.save(primaryEvaluationDTO);
        return ResponseEntity.created(new URI("/api/primary-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /primary-evaluations : Updates an existing primaryEvaluation.
     *
     * @param primaryEvaluationDTO the primaryEvaluationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated primaryEvaluationDTO,
     * or with status 400 (Bad Request) if the primaryEvaluationDTO is not valid,
     * or with status 500 (Internal Server Error) if the primaryEvaluationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/primary-evaluations")
    @Timed
    public ResponseEntity<PrimaryEvaluationDTO> updatePrimaryEvaluation(@RequestBody PrimaryEvaluationDTO primaryEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update PrimaryEvaluation : {}", primaryEvaluationDTO);
        if (primaryEvaluationDTO.getId() == null) {
            return createPrimaryEvaluation(primaryEvaluationDTO);
        }
        PrimaryEvaluationDTO result = primaryEvaluationService.save(primaryEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, primaryEvaluationDTO.getId().toString()))
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
    public ResponseEntity<List<PrimaryEvaluationDTO>> getAllPrimaryEvaluations(Pageable pageable) {
        log.debug("REST request to get a page of PrimaryEvaluations");
        Page<PrimaryEvaluationDTO> page = primaryEvaluationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/primary-evaluations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /primary-evaluations/:id : get the "id" primaryEvaluation.
     *
     * @param id the id of the primaryEvaluationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the primaryEvaluationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/primary-evaluations/{id}")
    @Timed
    public ResponseEntity<PrimaryEvaluationDTO> getPrimaryEvaluation(@PathVariable Long id) {
        log.debug("REST request to get PrimaryEvaluation : {}", id);
        PrimaryEvaluationDTO primaryEvaluationDTO = primaryEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(primaryEvaluationDTO));
    }

    /**
     * DELETE  /primary-evaluations/:id : delete the "id" primaryEvaluation.
     *
     * @param id the id of the primaryEvaluationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/primary-evaluations/{id}")
    @Timed
    public ResponseEntity<Void> deletePrimaryEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete PrimaryEvaluation : {}", id);
        primaryEvaluationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
