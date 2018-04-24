package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.PrimaryEvaluation;
import com.predix.bidopscore.repository.PrimaryEvaluationRepository;
import com.predix.bidopscore.service.PrimaryEvaluationService;
import com.predix.bidopscore.service.dto.PrimaryEvaluationDTO;
import com.predix.bidopscore.service.mapper.PrimaryEvaluationMapper;
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
 * Test class for the PrimaryEvaluationResource REST controller.
 *
 * @see PrimaryEvaluationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class PrimaryEvaluationResourceIntTest {

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
    private PrimaryEvaluationRepository primaryEvaluationRepository;

    @Autowired
    private PrimaryEvaluationMapper primaryEvaluationMapper;

    @Autowired
    private PrimaryEvaluationService primaryEvaluationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrimaryEvaluationMockMvc;

    private PrimaryEvaluation primaryEvaluation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrimaryEvaluationResource primaryEvaluationResource = new PrimaryEvaluationResource(primaryEvaluationService);
        this.restPrimaryEvaluationMockMvc = MockMvcBuilders.standaloneSetup(primaryEvaluationResource)
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
    public static PrimaryEvaluation createEntity(EntityManager em) {
        PrimaryEvaluation primaryEvaluation = new PrimaryEvaluation()
            .userId(DEFAULT_USER_ID)
            .solicitationId(DEFAULT_SOLICITATION_ID)
            .docURL(DEFAULT_DOC_URL)
            .categorySolicitations(DEFAULT_CATEGORY_SOLICITATIONS)
            .status(DEFAULT_STATUS);
        return primaryEvaluation;
    }

    @Before
    public void initTest() {
        primaryEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrimaryEvaluation() throws Exception {
        int databaseSizeBeforeCreate = primaryEvaluationRepository.findAll().size();

        // Create the PrimaryEvaluation
        PrimaryEvaluationDTO primaryEvaluationDTO = primaryEvaluationMapper.toDto(primaryEvaluation);
        restPrimaryEvaluationMockMvc.perform(post("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the PrimaryEvaluation in the database
        List<PrimaryEvaluation> primaryEvaluationList = primaryEvaluationRepository.findAll();
        assertThat(primaryEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        PrimaryEvaluation testPrimaryEvaluation = primaryEvaluationList.get(primaryEvaluationList.size() - 1);
        assertThat(testPrimaryEvaluation.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPrimaryEvaluation.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
        assertThat(testPrimaryEvaluation.getDocURL()).isEqualTo(DEFAULT_DOC_URL);
        assertThat(testPrimaryEvaluation.getCategorySolicitations()).isEqualTo(DEFAULT_CATEGORY_SOLICITATIONS);
        assertThat(testPrimaryEvaluation.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPrimaryEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primaryEvaluationRepository.findAll().size();

        // Create the PrimaryEvaluation with an existing ID
        primaryEvaluation.setId(1L);
        PrimaryEvaluationDTO primaryEvaluationDTO = primaryEvaluationMapper.toDto(primaryEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimaryEvaluationMockMvc.perform(post("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrimaryEvaluation in the database
        List<PrimaryEvaluation> primaryEvaluationList = primaryEvaluationRepository.findAll();
        assertThat(primaryEvaluationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrimaryEvaluations() throws Exception {
        // Initialize the database
        primaryEvaluationRepository.saveAndFlush(primaryEvaluation);

        // Get all the primaryEvaluationList
        restPrimaryEvaluationMockMvc.perform(get("/api/primary-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(primaryEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].docURL").value(hasItem(DEFAULT_DOC_URL.toString())))
            .andExpect(jsonPath("$.[*].categorySolicitations").value(hasItem(DEFAULT_CATEGORY_SOLICITATIONS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPrimaryEvaluation() throws Exception {
        // Initialize the database
        primaryEvaluationRepository.saveAndFlush(primaryEvaluation);

        // Get the primaryEvaluation
        restPrimaryEvaluationMockMvc.perform(get("/api/primary-evaluations/{id}", primaryEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(primaryEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()))
            .andExpect(jsonPath("$.docURL").value(DEFAULT_DOC_URL.toString()))
            .andExpect(jsonPath("$.categorySolicitations").value(DEFAULT_CATEGORY_SOLICITATIONS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrimaryEvaluation() throws Exception {
        // Get the primaryEvaluation
        restPrimaryEvaluationMockMvc.perform(get("/api/primary-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrimaryEvaluation() throws Exception {
        // Initialize the database
        primaryEvaluationRepository.saveAndFlush(primaryEvaluation);
        int databaseSizeBeforeUpdate = primaryEvaluationRepository.findAll().size();

        // Update the primaryEvaluation
        PrimaryEvaluation updatedPrimaryEvaluation = primaryEvaluationRepository.findOne(primaryEvaluation.getId());
        // Disconnect from session so that the updates on updatedPrimaryEvaluation are not directly saved in db
        em.detach(updatedPrimaryEvaluation);
        updatedPrimaryEvaluation
            .userId(UPDATED_USER_ID)
            .solicitationId(UPDATED_SOLICITATION_ID)
            .docURL(UPDATED_DOC_URL)
            .categorySolicitations(UPDATED_CATEGORY_SOLICITATIONS)
            .status(UPDATED_STATUS);
        PrimaryEvaluationDTO primaryEvaluationDTO = primaryEvaluationMapper.toDto(updatedPrimaryEvaluation);

        restPrimaryEvaluationMockMvc.perform(put("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the PrimaryEvaluation in the database
        List<PrimaryEvaluation> primaryEvaluationList = primaryEvaluationRepository.findAll();
        assertThat(primaryEvaluationList).hasSize(databaseSizeBeforeUpdate);
        PrimaryEvaluation testPrimaryEvaluation = primaryEvaluationList.get(primaryEvaluationList.size() - 1);
        assertThat(testPrimaryEvaluation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPrimaryEvaluation.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
        assertThat(testPrimaryEvaluation.getDocURL()).isEqualTo(UPDATED_DOC_URL);
        assertThat(testPrimaryEvaluation.getCategorySolicitations()).isEqualTo(UPDATED_CATEGORY_SOLICITATIONS);
        assertThat(testPrimaryEvaluation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPrimaryEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = primaryEvaluationRepository.findAll().size();

        // Create the PrimaryEvaluation
        PrimaryEvaluationDTO primaryEvaluationDTO = primaryEvaluationMapper.toDto(primaryEvaluation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrimaryEvaluationMockMvc.perform(put("/api/primary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(primaryEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the PrimaryEvaluation in the database
        List<PrimaryEvaluation> primaryEvaluationList = primaryEvaluationRepository.findAll();
        assertThat(primaryEvaluationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrimaryEvaluation() throws Exception {
        // Initialize the database
        primaryEvaluationRepository.saveAndFlush(primaryEvaluation);
        int databaseSizeBeforeDelete = primaryEvaluationRepository.findAll().size();

        // Get the primaryEvaluation
        restPrimaryEvaluationMockMvc.perform(delete("/api/primary-evaluations/{id}", primaryEvaluation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrimaryEvaluation> primaryEvaluationList = primaryEvaluationRepository.findAll();
        assertThat(primaryEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimaryEvaluation.class);
        PrimaryEvaluation primaryEvaluation1 = new PrimaryEvaluation();
        primaryEvaluation1.setId(1L);
        PrimaryEvaluation primaryEvaluation2 = new PrimaryEvaluation();
        primaryEvaluation2.setId(primaryEvaluation1.getId());
        assertThat(primaryEvaluation1).isEqualTo(primaryEvaluation2);
        primaryEvaluation2.setId(2L);
        assertThat(primaryEvaluation1).isNotEqualTo(primaryEvaluation2);
        primaryEvaluation1.setId(null);
        assertThat(primaryEvaluation1).isNotEqualTo(primaryEvaluation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimaryEvaluationDTO.class);
        PrimaryEvaluationDTO primaryEvaluationDTO1 = new PrimaryEvaluationDTO();
        primaryEvaluationDTO1.setId(1L);
        PrimaryEvaluationDTO primaryEvaluationDTO2 = new PrimaryEvaluationDTO();
        assertThat(primaryEvaluationDTO1).isNotEqualTo(primaryEvaluationDTO2);
        primaryEvaluationDTO2.setId(primaryEvaluationDTO1.getId());
        assertThat(primaryEvaluationDTO1).isEqualTo(primaryEvaluationDTO2);
        primaryEvaluationDTO2.setId(2L);
        assertThat(primaryEvaluationDTO1).isNotEqualTo(primaryEvaluationDTO2);
        primaryEvaluationDTO1.setId(null);
        assertThat(primaryEvaluationDTO1).isNotEqualTo(primaryEvaluationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(primaryEvaluationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(primaryEvaluationMapper.fromId(null)).isNull();
    }
}
