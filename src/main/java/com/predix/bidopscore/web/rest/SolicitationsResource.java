package com.predix.bidopscore.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.bidopscore.service.SolicitationsService;
import com.predix.bidopscore.web.rest.errors.BadRequestAlertException;
import com.predix.bidopscore.web.rest.util.HeaderUtil;
import com.predix.bidopscore.web.rest.util.PaginationUtil;
import com.predix.bidopscore.service.dto.SolicitationsDTO;
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
 * REST controller for managing Solicitations.
 */
@RestController
@RequestMapping("/api")
public class SolicitationsResource {

    private final Logger log = LoggerFactory.getLogger(SolicitationsResource.class);

    private static final String ENTITY_NAME = "solicitations";

    private final SolicitationsService solicitationsService;

    public SolicitationsResource(SolicitationsService solicitationsService) {
        this.solicitationsService = solicitationsService;
    }

    /**
     * POST  /solicitations : Create a new solicitations.
     *
     * @param solicitationsDTO the solicitationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solicitationsDTO, or with status 400 (Bad Request) if the solicitations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solicitations")
    @Timed
    public ResponseEntity<SolicitationsDTO> createSolicitations(@RequestBody SolicitationsDTO solicitationsDTO) throws URISyntaxException {
        log.debug("REST request to save Solicitations : {}", solicitationsDTO);
        if (solicitationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitationsDTO result = solicitationsService.save(solicitationsDTO);
        return ResponseEntity.created(new URI("/api/solicitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solicitations : Updates an existing solicitations.
     *
     * @param solicitationsDTO the solicitationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solicitationsDTO,
     * or with status 400 (Bad Request) if the solicitationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the solicitationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solicitations")
    @Timed
    public ResponseEntity<SolicitationsDTO> updateSolicitations(@RequestBody SolicitationsDTO solicitationsDTO) throws URISyntaxException {
        log.debug("REST request to update Solicitations : {}", solicitationsDTO);
        if (solicitationsDTO.getId() == null) {
            return createSolicitations(solicitationsDTO);
        }
        SolicitationsDTO result = solicitationsService.save(solicitationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solicitationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solicitations : get all the solicitations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solicitations in body
     */
    @GetMapping("/solicitations")
    @Timed
    public ResponseEntity<List<SolicitationsDTO>> getAllSolicitations(Pageable pageable) {
        log.debug("REST request to get a page of Solicitations");
        Page<SolicitationsDTO> page = solicitationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solicitations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solicitations/:id : get the "id" solicitations.
     *
     * @param id the id of the solicitationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solicitationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/solicitations/{id}")
    @Timed
    public ResponseEntity<SolicitationsDTO> getSolicitations(@PathVariable Long id) {
        log.debug("REST request to get Solicitations : {}", id);
        SolicitationsDTO solicitationsDTO = solicitationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solicitationsDTO));
    }

    /**
     * DELETE  /solicitations/:id : delete the "id" solicitations.
     *
     * @param id the id of the solicitationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solicitations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolicitations(@PathVariable Long id) {
        log.debug("REST request to delete Solicitations : {}", id);
        solicitationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
