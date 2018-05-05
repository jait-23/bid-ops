package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.SecondaryEvaluationService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.SecondaryEvaluationDTO;
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
 * REST controller for managing SecondaryEvaluation.
 */
@RestController
@RequestMapping("/api")
public class SecondaryEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(SecondaryEvaluationResource.class);

    private static final String ENTITY_NAME = "secondaryEvaluation";

    private final SecondaryEvaluationService secondaryEvaluationService;

    public SecondaryEvaluationResource(SecondaryEvaluationService secondaryEvaluationService) {
        this.secondaryEvaluationService = secondaryEvaluationService;
    }

    /**
     * POST  /secondary-evaluations : Create a new secondaryEvaluation.
     *
     * @param secondaryEvaluationDTO the secondaryEvaluationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new secondaryEvaluationDTO, or with status 400 (Bad Request) if the secondaryEvaluation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/secondary-evaluations")
    @Timed
    public ResponseEntity<SecondaryEvaluationDTO> createSecondaryEvaluation(@RequestBody SecondaryEvaluationDTO secondaryEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save SecondaryEvaluation : {}", secondaryEvaluationDTO);
        if (secondaryEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new secondaryEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecondaryEvaluationDTO result = secondaryEvaluationService.save(secondaryEvaluationDTO);
        return ResponseEntity.created(new URI("/api/secondary-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /secondary-evaluations : Updates an existing secondaryEvaluation.
     *
     * @param secondaryEvaluationDTO the secondaryEvaluationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated secondaryEvaluationDTO,
     * or with status 400 (Bad Request) if the secondaryEvaluationDTO is not valid,
     * or with status 500 (Internal Server Error) if the secondaryEvaluationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/secondary-evaluations")
    @Timed
    public ResponseEntity<SecondaryEvaluationDTO> updateSecondaryEvaluation(@RequestBody SecondaryEvaluationDTO secondaryEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update SecondaryEvaluation : {}", secondaryEvaluationDTO);
        if (secondaryEvaluationDTO.getId() == null) {
            return createSecondaryEvaluation(secondaryEvaluationDTO);
        }
        SecondaryEvaluationDTO result = secondaryEvaluationService.save(secondaryEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, secondaryEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /secondary-evaluations : get all the secondaryEvaluations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of secondaryEvaluations in body
     */
    @GetMapping("/secondary-evaluations")
    @Timed
    public ResponseEntity<List<SecondaryEvaluationDTO>> getAllSecondaryEvaluations(Pageable pageable) {
        log.debug("REST request to get a page of SecondaryEvaluations");
        Page<SecondaryEvaluationDTO> page = secondaryEvaluationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/secondary-evaluations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /secondary-evaluations/:id : get the "id" secondaryEvaluation.
     *
     * @param id the id of the secondaryEvaluationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the secondaryEvaluationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/secondary-evaluations/{id}")
    @Timed
    public ResponseEntity<SecondaryEvaluationDTO> getSecondaryEvaluation(@PathVariable Long id) {
        log.debug("REST request to get SecondaryEvaluation : {}", id);
        SecondaryEvaluationDTO secondaryEvaluationDTO = secondaryEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(secondaryEvaluationDTO));
    }

    /**
     * DELETE  /secondary-evaluations/:id : delete the "id" secondaryEvaluation.
     *
     * @param id the id of the secondaryEvaluationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/secondary-evaluations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSecondaryEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete SecondaryEvaluation : {}", id);
        secondaryEvaluationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
