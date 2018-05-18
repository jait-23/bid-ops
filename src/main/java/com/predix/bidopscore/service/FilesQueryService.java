package com.predix.bidopscore.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.predix.bidopscore.domain.Files;
import com.predix.bidopscore.domain.*; // for static metamodels
import com.predix.bidopscore.repository.FilesRepository;
import com.predix.bidopscore.service.dto.FilesCriteria;

import com.predix.bidopscore.service.dto.FilesDTO;
import com.predix.bidopscore.service.mapper.FilesMapper;

/**
 * Service for executing complex queries for Files entities in the database.
 * The main input is a {@link FilesCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FilesDTO} or a {@link Page} of {@link FilesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FilesQueryService extends QueryService<Files> {

    private final Logger log = LoggerFactory.getLogger(FilesQueryService.class);


    private final FilesRepository filesRepository;

    private final FilesMapper filesMapper;

    public FilesQueryService(FilesRepository filesRepository, FilesMapper filesMapper) {
        this.filesRepository = filesRepository;
        this.filesMapper = filesMapper;
    }

    /**
     * Return a {@link List} of {@link FilesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FilesDTO> findByCriteria(FilesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Files> specification = createSpecification(criteria);
        return filesMapper.toDto(filesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FilesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FilesDTO> findByCriteria(FilesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Files> specification = createSpecification(criteria);
        final Page<Files> result = filesRepository.findAll(specification, page);
        return result.map(filesMapper::toDto);
    }

    /**
     * Function to convert FilesCriteria to a {@link Specifications}
     */
    private Specifications<Files> createSpecification(FilesCriteria criteria) {
        Specifications<Files> specification = Specifications.where(null);
        /* if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Files_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Files_.type));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), Files_.documentName));
            }
        } */
        return specification;
    }

}
