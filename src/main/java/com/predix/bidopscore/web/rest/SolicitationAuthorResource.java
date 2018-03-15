package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.SolicitationAuthorService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.SolicitationAuthorDTO;
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
 * REST controller for managing SolicitationAuthor.
 */
@RestController
@RequestMapping("/api")
public class SolicitationAuthorResource {

    private final Logger log = LoggerFactory.getLogger(SolicitationAuthorResource.class);

    private static final String ENTITY_NAME = "solicitationAuthor";

    private final SolicitationAuthorService solicitationAuthorService;

    public SolicitationAuthorResource(SolicitationAuthorService solicitationAuthorService) {
        this.solicitationAuthorService = solicitationAuthorService;
    }

    /**
     * POST  /solicitation-authors : Create a new solicitationAuthor.
     *
     * @param solicitationAuthorDTO the solicitationAuthorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solicitationAuthorDTO, or with status 400 (Bad Request) if the solicitationAuthor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solicitation-authors")
    @Timed
    public ResponseEntity<SolicitationAuthorDTO> createSolicitationAuthor(@RequestBody SolicitationAuthorDTO solicitationAuthorDTO) throws URISyntaxException {
        log.debug("REST request to save SolicitationAuthor : {}", solicitationAuthorDTO);
        if (solicitationAuthorDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitationAuthor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitationAuthorDTO result = solicitationAuthorService.save(solicitationAuthorDTO);
        return ResponseEntity.created(new URI("/api/solicitation-authors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solicitation-authors : Updates an existing solicitationAuthor.
     *
     * @param solicitationAuthorDTO the solicitationAuthorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solicitationAuthorDTO,
     * or with status 400 (Bad Request) if the solicitationAuthorDTO is not valid,
     * or with status 500 (Internal Server Error) if the solicitationAuthorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solicitation-authors")
    @Timed
    public ResponseEntity<SolicitationAuthorDTO> updateSolicitationAuthor(@RequestBody SolicitationAuthorDTO solicitationAuthorDTO) throws URISyntaxException {
        log.debug("REST request to update SolicitationAuthor : {}", solicitationAuthorDTO);
        if (solicitationAuthorDTO.getId() == null) {
            return createSolicitationAuthor(solicitationAuthorDTO);
        }
        SolicitationAuthorDTO result = solicitationAuthorService.save(solicitationAuthorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solicitationAuthorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solicitation-authors : get all the solicitationAuthors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solicitationAuthors in body
     */
    @GetMapping("/solicitation-authors")
    @Timed
    public ResponseEntity<List<SolicitationAuthorDTO>> getAllSolicitationAuthors(Pageable pageable) {
        log.debug("REST request to get a page of SolicitationAuthors");
        Page<SolicitationAuthorDTO> page = solicitationAuthorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solicitation-authors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solicitation-authors/:id : get the "id" solicitationAuthor.
     *
     * @param id the id of the solicitationAuthorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solicitationAuthorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/solicitation-authors/{id}")
    @Timed
    public ResponseEntity<SolicitationAuthorDTO> getSolicitationAuthor(@PathVariable Long id) {
        log.debug("REST request to get SolicitationAuthor : {}", id);
        SolicitationAuthorDTO solicitationAuthorDTO = solicitationAuthorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solicitationAuthorDTO));
    }

    /**
     * DELETE  /solicitation-authors/:id : delete the "id" solicitationAuthor.
     *
     * @param id the id of the solicitationAuthorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solicitation-authors/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolicitationAuthor(@PathVariable Long id) {
        log.debug("REST request to delete SolicitationAuthor : {}", id);
        solicitationAuthorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
