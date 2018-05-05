package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.SecondaryEvaluation;
import com.predix.bidopscore.repository.SecondaryEvaluationRepository;
import com.predix.bidopscore.service.SecondaryEvaluationService;
import com.predix.bidopscore.service.dto.SecondaryEvaluationDTO;
import com.predix.bidopscore.service.mapper.SecondaryEvaluationMapper;
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
 * Test class for the SecondaryEvaluationResource REST controller.
 *
 * @see SecondaryEvaluationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class SecondaryEvaluationResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_EVALUATOR_ID = 1L;
    private static final Long UPDATED_EVALUATOR_ID = 2L;

    private static final String DEFAULT_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_DOC_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final String DEFAULT_ELIGIBLE = "AAAAAAAAAA";
    private static final String UPDATED_ELIGIBLE = "BBBBBBBBBB";

    @Autowired
    private SecondaryEvaluationRepository secondaryEvaluationRepository;

    @Autowired
    private SecondaryEvaluationMapper secondaryEvaluationMapper;

    @Autowired
    private SecondaryEvaluationService secondaryEvaluationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSecondaryEvaluationMockMvc;

    private SecondaryEvaluation secondaryEvaluation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SecondaryEvaluationResource secondaryEvaluationResource = new SecondaryEvaluationResource(secondaryEvaluationService);
        this.restSecondaryEvaluationMockMvc = MockMvcBuilders.standaloneSetup(secondaryEvaluationResource)
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
    public static SecondaryEvaluation createEntity(EntityManager em) {
        SecondaryEvaluation secondaryEvaluation = new SecondaryEvaluation()
            .user_id(DEFAULT_USER_ID)
            .evaluator_id(DEFAULT_EVALUATOR_ID)
            .doc_url(DEFAULT_DOC_URL)
            .solicitation_id(DEFAULT_SOLICITATION_ID)
            .score(DEFAULT_SCORE)
            .eligible(DEFAULT_ELIGIBLE);
        return secondaryEvaluation;
    }

    @Before
    public void initTest() {
        secondaryEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecondaryEvaluation() throws Exception {
        int databaseSizeBeforeCreate = secondaryEvaluationRepository.findAll().size();

        // Create the SecondaryEvaluation
        SecondaryEvaluationDTO secondaryEvaluationDTO = secondaryEvaluationMapper.toDto(secondaryEvaluation);
        restSecondaryEvaluationMockMvc.perform(post("/api/secondary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondaryEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the SecondaryEvaluation in the database
        List<SecondaryEvaluation> secondaryEvaluationList = secondaryEvaluationRepository.findAll();
        assertThat(secondaryEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        SecondaryEvaluation testSecondaryEvaluation = secondaryEvaluationList.get(secondaryEvaluationList.size() - 1);
        assertThat(testSecondaryEvaluation.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSecondaryEvaluation.getEvaluator_id()).isEqualTo(DEFAULT_EVALUATOR_ID);
        assertThat(testSecondaryEvaluation.getDoc_url()).isEqualTo(DEFAULT_DOC_URL);
        assertThat(testSecondaryEvaluation.getSolicitation_id()).isEqualTo(DEFAULT_SOLICITATION_ID);
        assertThat(testSecondaryEvaluation.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testSecondaryEvaluation.getEligible()).isEqualTo(DEFAULT_ELIGIBLE);
    }

    @Test
    @Transactional
    public void createSecondaryEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secondaryEvaluationRepository.findAll().size();

        // Create the SecondaryEvaluation with an existing ID
        secondaryEvaluation.setId(1L);
        SecondaryEvaluationDTO secondaryEvaluationDTO = secondaryEvaluationMapper.toDto(secondaryEvaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecondaryEvaluationMockMvc.perform(post("/api/secondary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondaryEvaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecondaryEvaluation in the database
        List<SecondaryEvaluation> secondaryEvaluationList = secondaryEvaluationRepository.findAll();
        assertThat(secondaryEvaluationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSecondaryEvaluations() throws Exception {
        // Initialize the database
        secondaryEvaluationRepository.saveAndFlush(secondaryEvaluation);

        // Get all the secondaryEvaluationList
        restSecondaryEvaluationMockMvc.perform(get("/api/secondary-evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secondaryEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].evaluator_id").value(hasItem(DEFAULT_EVALUATOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].doc_url").value(hasItem(DEFAULT_DOC_URL.toString())))
            .andExpect(jsonPath("$.[*].solicitation_id").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].eligible").value(hasItem(DEFAULT_ELIGIBLE.toString())));
    }

    @Test
    @Transactional
    public void getSecondaryEvaluation() throws Exception {
        // Initialize the database
        secondaryEvaluationRepository.saveAndFlush(secondaryEvaluation);

        // Get the secondaryEvaluation
        restSecondaryEvaluationMockMvc.perform(get("/api/secondary-evaluations/{id}", secondaryEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(secondaryEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.evaluator_id").value(DEFAULT_EVALUATOR_ID.intValue()))
            .andExpect(jsonPath("$.doc_url").value(DEFAULT_DOC_URL.toString()))
            .andExpect(jsonPath("$.solicitation_id").value(DEFAULT_SOLICITATION_ID.intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.eligible").value(DEFAULT_ELIGIBLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecondaryEvaluation() throws Exception {
        // Get the secondaryEvaluation
        restSecondaryEvaluationMockMvc.perform(get("/api/secondary-evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecondaryEvaluation() throws Exception {
        // Initialize the database
        secondaryEvaluationRepository.saveAndFlush(secondaryEvaluation);
        int databaseSizeBeforeUpdate = secondaryEvaluationRepository.findAll().size();

        // Update the secondaryEvaluation
        SecondaryEvaluation updatedSecondaryEvaluation = secondaryEvaluationRepository.findOne(secondaryEvaluation.getId());
        // Disconnect from session so that the updates on updatedSecondaryEvaluation are not directly saved in db
        em.detach(updatedSecondaryEvaluation);
        updatedSecondaryEvaluation
            .user_id(UPDATED_USER_ID)
            .evaluator_id(UPDATED_EVALUATOR_ID)
            .doc_url(UPDATED_DOC_URL)
            .solicitation_id(UPDATED_SOLICITATION_ID)
            .score(UPDATED_SCORE)
            .eligible(UPDATED_ELIGIBLE);
        SecondaryEvaluationDTO secondaryEvaluationDTO = secondaryEvaluationMapper.toDto(updatedSecondaryEvaluation);

        restSecondaryEvaluationMockMvc.perform(put("/api/secondary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondaryEvaluationDTO)))
            .andExpect(status().isOk());

        // Validate the SecondaryEvaluation in the database
        List<SecondaryEvaluation> secondaryEvaluationList = secondaryEvaluationRepository.findAll();
        assertThat(secondaryEvaluationList).hasSize(databaseSizeBeforeUpdate);
        SecondaryEvaluation testSecondaryEvaluation = secondaryEvaluationList.get(secondaryEvaluationList.size() - 1);
        assertThat(testSecondaryEvaluation.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSecondaryEvaluation.getEvaluator_id()).isEqualTo(UPDATED_EVALUATOR_ID);
        assertThat(testSecondaryEvaluation.getDoc_url()).isEqualTo(UPDATED_DOC_URL);
        assertThat(testSecondaryEvaluation.getSolicitation_id()).isEqualTo(UPDATED_SOLICITATION_ID);
        assertThat(testSecondaryEvaluation.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSecondaryEvaluation.getEligible()).isEqualTo(UPDATED_ELIGIBLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSecondaryEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = secondaryEvaluationRepository.findAll().size();

        // Create the SecondaryEvaluation
        SecondaryEvaluationDTO secondaryEvaluationDTO = secondaryEvaluationMapper.toDto(secondaryEvaluation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSecondaryEvaluationMockMvc.perform(put("/api/secondary-evaluations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondaryEvaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the SecondaryEvaluation in the database
        List<SecondaryEvaluation> secondaryEvaluationList = secondaryEvaluationRepository.findAll();
        assertThat(secondaryEvaluationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSecondaryEvaluation() throws Exception {
        // Initialize the database
        secondaryEvaluationRepository.saveAndFlush(secondaryEvaluation);
        int databaseSizeBeforeDelete = secondaryEvaluationRepository.findAll().size();

        // Get the secondaryEvaluation
        restSecondaryEvaluationMockMvc.perform(delete("/api/secondary-evaluations/{id}", secondaryEvaluation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SecondaryEvaluation> secondaryEvaluationList = secondaryEvaluationRepository.findAll();
        assertThat(secondaryEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecondaryEvaluation.class);
        SecondaryEvaluation secondaryEvaluation1 = new SecondaryEvaluation();
        secondaryEvaluation1.setId(1L);
        SecondaryEvaluation secondaryEvaluation2 = new SecondaryEvaluation();
        secondaryEvaluation2.setId(secondaryEvaluation1.getId());
        assertThat(secondaryEvaluation1).isEqualTo(secondaryEvaluation2);
        secondaryEvaluation2.setId(2L);
        assertThat(secondaryEvaluation1).isNotEqualTo(secondaryEvaluation2);
        secondaryEvaluation1.setId(null);
        assertThat(secondaryEvaluation1).isNotEqualTo(secondaryEvaluation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecondaryEvaluationDTO.class);
        SecondaryEvaluationDTO secondaryEvaluationDTO1 = new SecondaryEvaluationDTO();
        secondaryEvaluationDTO1.setId(1L);
        SecondaryEvaluationDTO secondaryEvaluationDTO2 = new SecondaryEvaluationDTO();
        assertThat(secondaryEvaluationDTO1).isNotEqualTo(secondaryEvaluationDTO2);
        secondaryEvaluationDTO2.setId(secondaryEvaluationDTO1.getId());
        assertThat(secondaryEvaluationDTO1).isEqualTo(secondaryEvaluationDTO2);
        secondaryEvaluationDTO2.setId(2L);
        assertThat(secondaryEvaluationDTO1).isNotEqualTo(secondaryEvaluationDTO2);
        secondaryEvaluationDTO1.setId(null);
        assertThat(secondaryEvaluationDTO1).isNotEqualTo(secondaryEvaluationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(secondaryEvaluationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(secondaryEvaluationMapper.fromId(null)).isNull();
    }
}
