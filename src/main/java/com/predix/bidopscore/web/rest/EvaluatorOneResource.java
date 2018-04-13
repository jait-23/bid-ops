package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.EvaluatorOneService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.EvaluatorOneDTO;
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
 * REST controller for managing EvaluatorOne.
 */
@RestController
@RequestMapping("/api")
public class EvaluatorOneResource {

    private final Logger log = LoggerFactory.getLogger(EvaluatorOneResource.class);

    private static final String ENTITY_NAME = "evaluatorOne";

    private final EvaluatorOneService evaluatorOneService;

    public EvaluatorOneResource(EvaluatorOneService evaluatorOneService) {
        this.evaluatorOneService = evaluatorOneService;
    }

    /**
     * POST  /evaluator-ones : Create a new evaluatorOne.
     *
     * @param evaluatorOneDTO the evaluatorOneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluatorOneDTO, or with status 400 (Bad Request) if the evaluatorOne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluator-ones")
    @Timed
    public ResponseEntity<EvaluatorOneDTO> createEvaluatorOne(@RequestBody EvaluatorOneDTO evaluatorOneDTO) throws URISyntaxException {
        log.debug("REST request to save EvaluatorOne : {}", evaluatorOneDTO);
        if (evaluatorOneDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluatorOne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluatorOneDTO result = evaluatorOneService.save(evaluatorOneDTO);
        return ResponseEntity.created(new URI("/api/evaluator-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluator-ones : Updates an existing evaluatorOne.
     *
     * @param evaluatorOneDTO the evaluatorOneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluatorOneDTO,
     * or with status 400 (Bad Request) if the evaluatorOneDTO is not valid,
     * or with status 500 (Internal Server Error) if the evaluatorOneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluator-ones")
    @Timed
    public ResponseEntity<EvaluatorOneDTO> updateEvaluatorOne(@RequestBody EvaluatorOneDTO evaluatorOneDTO) throws URISyntaxException {
        log.debug("REST request to update EvaluatorOne : {}", evaluatorOneDTO);
        if (evaluatorOneDTO.getId() == null) {
            return createEvaluatorOne(evaluatorOneDTO);
        }
        EvaluatorOneDTO result = evaluatorOneService.save(evaluatorOneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluatorOneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluator-ones : get all the evaluatorOnes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of evaluatorOnes in body
     */
    @GetMapping("/evaluator-ones")
    @Timed
    public ResponseEntity<List<EvaluatorOneDTO>> getAllEvaluatorOnes(Pageable pageable) {
        log.debug("REST request to get a page of EvaluatorOnes");
        Page<EvaluatorOneDTO> page = evaluatorOneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluator-ones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evaluator-ones/:id : get the "id" evaluatorOne.
     *
     * @param id the id of the evaluatorOneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluatorOneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/evaluator-ones/{id}")
    @Timed
    public ResponseEntity<EvaluatorOneDTO> getEvaluatorOne(@PathVariable Long id) {
        log.debug("REST request to get EvaluatorOne : {}", id);
        EvaluatorOneDTO evaluatorOneDTO = evaluatorOneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(evaluatorOneDTO));
    }

    /**
     * DELETE  /evaluator-ones/:id : delete the "id" evaluatorOne.
     *
     * @param id the id of the evaluatorOneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluator-ones/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluatorOne(@PathVariable Long id) {
        log.debug("REST request to delete EvaluatorOne : {}", id);
        evaluatorOneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
