package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.PrimaryEvaluations;
import com.predix.bidopscore.repository.PrimaryEvaluationsRepository;
import com.predix.bidopscore.service.PrimaryEvaluationsService;
import com.predix.bidopscore.service.dto.PrimaryEvaluationsDTO;
import com.predix.bidopscore.service.mapper.PrimaryEvaluationsMapper;
import com.predix.bidopscore.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.predix.bidopscore.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrimaryEvaluationsResource REST controller.
 *
 * @see PrimaryEvaluationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class PrimaryEvaluationsResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    private static final String DEFAULT_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_DOC_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_SOLICITATIONS = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_SOLICITATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private PrimaryEvaluationsRepository primaryEvaluationsRepository;

    @Autowired
    private PrimaryEvaluationsMapper primaryEvaluationsMapper;

    @Autowired
    private PrimaryEvaluationsService primaryEvaluationsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrimaryEvaluationsMockMvc;

    private PrimaryEvaluations primaryEvaluations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrimaryEvaluationsResource primaryEvaluationsResource = new PrimaryEvaluationsResource(primaryEvaluationsService);
        this.restPrimaryEvaluationsMockMvc = MockMvcBuilders.standaloneSetup(primaryEvaluationsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrimaryEvaluations createEntity(EntityManager em) {
        PrimaryEvaluations primaryEvaluations = new PrimaryEvaluations()
            .userId(DEFAULT_USER_ID)
            .solicitationId(DEFAULT_SOLICITATION_ID)
            .docURL(DEFAULT_DOC_URL)
            .categorySolicitations(DEFAULT_CATEGORY_SOLICITATIONS)
            .status(DEFAULT_STATUS);
        return primaryEvaluations;
    }

    @Before
    public void initTest() {
        primaryEvaluations = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrimaryEvaluations() throws Exception {
        int databaseSizeBeforeCreate = primaryEvaluationsRepository.findAll().size();

        // Create the PrimaryEvaluations
        PrimaryEvaluationsDTO primaryEvaluationsDTO = primaryEvaluationsMapper.toDto(primaryEvaluations);
        restPrimaryEvaluationsMockMvc.perform(post("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationsDTO)))
            .andExpect(status().isCreated());

        // Validate the PrimaryEvaluations in the database
        List<PrimaryEvaluations> primaryEvaluationsList = primaryEvaluationsRepository.findAll();
        assertThat(primaryEvaluationsList).hasSize(databaseSizeBeforeCreate + 1);
        PrimaryEvaluations testPrimaryEvaluations = primaryEvaluationsList.get(primaryEvaluationsList.size() - 1);
        assertThat(testPrimaryEvaluations.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPrimaryEvaluations.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
        assertThat(testPrimaryEvaluations.getDocURL()).isEqualTo(DEFAULT_DOC_URL);
        assertThat(testPrimaryEvaluations.getCategorySolicitations()).isEqualTo(DEFAULT_CATEGORY_SOLICITATIONS);
        assertThat(testPrimaryEvaluations.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPrimaryEvaluationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primaryEvaluationsRepository.findAll().size();

        // Create the PrimaryEvaluations with an existing ID
        primaryEvaluations.setId(1L);
        PrimaryEvaluationsDTO primaryEvaluationsDTO = primaryEvaluationsMapper.toDto(primaryEvaluations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimaryEvaluationsMockMvc.perform(post("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrimaryEvaluations in the database
        List<PrimaryEvaluations> primaryEvaluationsList = primaryEvaluationsRepository.findAll();
        assertThat(primaryEvaluationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrimaryEvaluations() throws Exception {
        // Initialize the database
        primaryEvaluationsRepository.saveAndFlush(primaryEvaluations);

        // Get all the primaryEvaluationsList
        restPrimaryEvaluationsMockMvc.perform(get("/api/primary-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(primaryEvaluations.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].docURL").value(hasItem(DEFAULT_DOC_URL.toString())))
            .andExpect(jsonPath("$.[*].categorySolicitations").value(hasItem(DEFAULT_CATEGORY_SOLICITATIONS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPrimaryEvaluations() throws Exception {
        // Initialize the database
        primaryEvaluationsRepository.saveAndFlush(primaryEvaluations);

        // Get the primaryEvaluations
        restPrimaryEvaluationsMockMvc.perform(get("/api/primary-evaluations/{id}", primaryEvaluations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(primaryEvaluations.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()))
            .andExpect(jsonPath("$.docURL").value(DEFAULT_DOC_URL.toString()))
            .andExpect(jsonPath("$.categorySolicitations").value(DEFAULT_CATEGORY_SOLICITATIONS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrimaryEvaluations() throws Exception {
        // Get the primaryEvaluations
        restPrimaryEvaluationsMockMvc.perform(get("/api/primary-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrimaryEvaluations() throws Exception {
        // Initialize the database
        primaryEvaluationsRepository.saveAndFlush(primaryEvaluations);
        int databaseSizeBeforeUpdate = primaryEvaluationsRepository.findAll().size();

        // Update the primaryEvaluations
        PrimaryEvaluations updatedPrimaryEvaluations = primaryEvaluationsRepository.findOne(primaryEvaluations.getId());
        // Disconnect from session so that the updates on updatedPrimaryEvaluations are not directly saved in db
        em.detach(updatedPrimaryEvaluations);
        updatedPrimaryEvaluations
            .userId(UPDATED_USER_ID)
            .solicitationId(UPDATED_SOLICITATION_ID)
            .docURL(UPDATED_DOC_URL)
            .categorySolicitations(UPDATED_CATEGORY_SOLICITATIONS)
            .status(UPDATED_STATUS);
        PrimaryEvaluationsDTO primaryEvaluationsDTO = primaryEvaluationsMapper.toDto(updatedPrimaryEvaluations);

        restPrimaryEvaluationsMockMvc.perform(put("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationsDTO)))
            .andExpect(status().isOk());

        // Validate the PrimaryEvaluations in the database
        List<PrimaryEvaluations> primaryEvaluationsList = primaryEvaluationsRepository.findAll();
        assertThat(primaryEvaluationsList).hasSize(databaseSizeBeforeUpdate);
        PrimaryEvaluations testPrimaryEvaluations = primaryEvaluationsList.get(primaryEvaluationsList.size() - 1);
        assertThat(testPrimaryEvaluations.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPrimaryEvaluations.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
        assertThat(testPrimaryEvaluations.getDocURL()).isEqualTo(UPDATED_DOC_URL);
        assertThat(testPrimaryEvaluations.getCategorySolicitations()).isEqualTo(UPDATED_CATEGORY_SOLICITATIONS);
        assertThat(testPrimaryEvaluations.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPrimaryEvaluations() throws Exception {
        int databaseSizeBeforeUpdate = primaryEvaluationsRepository.findAll().size();

        // Create the PrimaryEvaluations
        PrimaryEvaluationsDTO primaryEvaluationsDTO = primaryEvaluationsMapper.toDto(primaryEvaluations);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrimaryEvaluationsMockMvc.perform(put("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationsDTO)))
            .andExpect(status().isCreated());

        // Validate the PrimaryEvaluations in the database
        List<PrimaryEvaluations> primaryEvaluationsList = primaryEvaluationsRepository.findAll();
        assertThat(primaryEvaluationsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrimaryEvaluations() throws Exception {
        // Initialize the database
        primaryEvaluationsRepository.saveAndFlush(primaryEvaluations);
        int databaseSizeBeforeDelete = primaryEvaluationsRepository.findAll().size();

        // Get the primaryEvaluations
        restPrimaryEvaluationsMockMvc.perform(delete("/api/primary-evaluations/{id}", primaryEvaluations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrimaryEvaluations> primaryEvaluationsList = primaryEvaluationsRepository.findAll();
        assertThat(primaryEvaluationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimaryEvaluations.class);
        PrimaryEvaluations primaryEvaluations1 = new PrimaryEvaluations();
        primaryEvaluations1.setId(1L);
        PrimaryEvaluations primaryEvaluations2 = new PrimaryEvaluations();
        primaryEvaluations2.setId(primaryEvaluations1.getId());
        assertThat(primaryEvaluations1).isEqualTo(primaryEvaluations2);
        primaryEvaluations2.setId(2L);
        assertThat(primaryEvaluations1).isNotEqualTo(primaryEvaluations2);
        primaryEvaluations1.setId(null);
        assertThat(primaryEvaluations1).isNotEqualTo(primaryEvaluations2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimaryEvaluationsDTO.class);
        PrimaryEvaluationsDTO primaryEvaluationsDTO1 = new PrimaryEvaluationsDTO();
        primaryEvaluationsDTO1.setId(1L);
        PrimaryEvaluationsDTO primaryEvaluationsDTO2 = new PrimaryEvaluationsDTO();
        assertThat(primaryEvaluationsDTO1).isNotEqualTo(primaryEvaluationsDTO2);
        primaryEvaluationsDTO2.setId(primaryEvaluationsDTO1.getId());
        assertThat(primaryEvaluationsDTO1).isEqualTo(primaryEvaluationsDTO2);
        primaryEvaluationsDTO2.setId(2L);
        assertThat(primaryEvaluationsDTO1).isNotEqualTo(primaryEvaluationsDTO2);
        primaryEvaluationsDTO1.setId(null);
        assertThat(primaryEvaluationsDTO1).isNotEqualTo(primaryEvaluationsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(primaryEvaluationsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(primaryEvaluationsMapper.fromId(null)).isNull();
    }
}
