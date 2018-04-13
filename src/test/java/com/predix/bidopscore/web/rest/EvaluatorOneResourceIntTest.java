package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.EvaluatorOne;
import com.predix.bidopscore.repository.EvaluatorOneRepository;
import com.predix.bidopscore.service.EvaluatorOneService;
import com.predix.bidopscore.service.dto.EvaluatorOneDTO;
import com.predix.bidopscore.service.mapper.EvaluatorOneMapper;
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
 * Test class for the EvaluatorOneResource REST controller.
 *
 * @see EvaluatorOneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class EvaluatorOneResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    private static final String DEFAULT_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_DOC_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_SOLICITATION = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_SOLICITATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private EvaluatorOneRepository evaluatorOneRepository;

    @Autowired
    private EvaluatorOneMapper evaluatorOneMapper;

    @Autowired
    private EvaluatorOneService evaluatorOneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEvaluatorOneMockMvc;

    private EvaluatorOne evaluatorOne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvaluatorOneResource evaluatorOneResource = new EvaluatorOneResource(evaluatorOneService);
        this.restEvaluatorOneMockMvc = MockMvcBuilders.standaloneSetup(evaluatorOneResource)
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
    public static EvaluatorOne createEntity(EntityManager em) {
        EvaluatorOne evaluatorOne = new EvaluatorOne()
            .userId(DEFAULT_USER_ID)
            .solicitationId(DEFAULT_SOLICITATION_ID)
            .docURL(DEFAULT_DOC_URL)
            .categorySolicitation(DEFAULT_CATEGORY_SOLICITATION)
            .status(DEFAULT_STATUS);
        return evaluatorOne;
    }

    @Before
    public void initTest() {
        evaluatorOne = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluatorOne() throws Exception {
        int databaseSizeBeforeCreate = evaluatorOneRepository.findAll().size();

        // Create the EvaluatorOne
        EvaluatorOneDTO evaluatorOneDTO = evaluatorOneMapper.toDto(evaluatorOne);
        restEvaluatorOneMockMvc.perform(post("/api/evaluator-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluatorOneDTO)))
            .andExpect(status().isCreated());

        // Validate the EvaluatorOne in the database
        List<EvaluatorOne> evaluatorOneList = evaluatorOneRepository.findAll();
        assertThat(evaluatorOneList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluatorOne testEvaluatorOne = evaluatorOneList.get(evaluatorOneList.size() - 1);
        assertThat(testEvaluatorOne.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEvaluatorOne.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
        assertThat(testEvaluatorOne.getDocURL()).isEqualTo(DEFAULT_DOC_URL);
        assertThat(testEvaluatorOne.getCategorySolicitation()).isEqualTo(DEFAULT_CATEGORY_SOLICITATION);
        assertThat(testEvaluatorOne.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEvaluatorOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluatorOneRepository.findAll().size();

        // Create the EvaluatorOne with an existing ID
        evaluatorOne.setId(1L);
        EvaluatorOneDTO evaluatorOneDTO = evaluatorOneMapper.toDto(evaluatorOne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluatorOneMockMvc.perform(post("/api/evaluator-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluatorOneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EvaluatorOne in the database
        List<EvaluatorOne> evaluatorOneList = evaluatorOneRepository.findAll();
        assertThat(evaluatorOneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvaluatorOnes() throws Exception {
        // Initialize the database
        evaluatorOneRepository.saveAndFlush(evaluatorOne);

        // Get all the evaluatorOneList
        restEvaluatorOneMockMvc.perform(get("/api/evaluator-ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluatorOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].docURL").value(hasItem(DEFAULT_DOC_URL.toString())))
            .andExpect(jsonPath("$.[*].categorySolicitation").value(hasItem(DEFAULT_CATEGORY_SOLICITATION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getEvaluatorOne() throws Exception {
        // Initialize the database
        evaluatorOneRepository.saveAndFlush(evaluatorOne);

        // Get the evaluatorOne
        restEvaluatorOneMockMvc.perform(get("/api/evaluator-ones/{id}", evaluatorOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evaluatorOne.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()))
            .andExpect(jsonPath("$.docURL").value(DEFAULT_DOC_URL.toString()))
            .andExpect(jsonPath("$.categorySolicitation").value(DEFAULT_CATEGORY_SOLICITATION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluatorOne() throws Exception {
        // Get the evaluatorOne
        restEvaluatorOneMockMvc.perform(get("/api/evaluator-ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluatorOne() throws Exception {
        // Initialize the database
        evaluatorOneRepository.saveAndFlush(evaluatorOne);
        int databaseSizeBeforeUpdate = evaluatorOneRepository.findAll().size();

        // Update the evaluatorOne
        EvaluatorOne updatedEvaluatorOne = evaluatorOneRepository.findOne(evaluatorOne.getId());
        // Disconnect from session so that the updates on updatedEvaluatorOne are not directly saved in db
        em.detach(updatedEvaluatorOne);
        updatedEvaluatorOne
            .userId(UPDATED_USER_ID)
            .solicitationId(UPDATED_SOLICITATION_ID)
            .docURL(UPDATED_DOC_URL)
            .categorySolicitation(UPDATED_CATEGORY_SOLICITATION)
            .status(UPDATED_STATUS);
        EvaluatorOneDTO evaluatorOneDTO = evaluatorOneMapper.toDto(updatedEvaluatorOne);

        restEvaluatorOneMockMvc.perform(put("/api/evaluator-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluatorOneDTO)))
            .andExpect(status().isOk());

        // Validate the EvaluatorOne in the database
        List<EvaluatorOne> evaluatorOneList = evaluatorOneRepository.findAll();
        assertThat(evaluatorOneList).hasSize(databaseSizeBeforeUpdate);
        EvaluatorOne testEvaluatorOne = evaluatorOneList.get(evaluatorOneList.size() - 1);
        assertThat(testEvaluatorOne.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEvaluatorOne.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
        assertThat(testEvaluatorOne.getDocURL()).isEqualTo(UPDATED_DOC_URL);
        assertThat(testEvaluatorOne.getCategorySolicitation()).isEqualTo(UPDATED_CATEGORY_SOLICITATION);
        assertThat(testEvaluatorOne.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluatorOne() throws Exception {
        int databaseSizeBeforeUpdate = evaluatorOneRepository.findAll().size();

        // Create the EvaluatorOne
        EvaluatorOneDTO evaluatorOneDTO = evaluatorOneMapper.toDto(evaluatorOne);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEvaluatorOneMockMvc.perform(put("/api/evaluator-ones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluatorOneDTO)))
            .andExpect(status().isCreated());

        // Validate the EvaluatorOne in the database
        List<EvaluatorOne> evaluatorOneList = evaluatorOneRepository.findAll();
        assertThat(evaluatorOneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEvaluatorOne() throws Exception {
        // Initialize the database
        evaluatorOneRepository.saveAndFlush(evaluatorOne);
        int databaseSizeBeforeDelete = evaluatorOneRepository.findAll().size();

        // Get the evaluatorOne
        restEvaluatorOneMockMvc.perform(delete("/api/evaluator-ones/{id}", evaluatorOne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EvaluatorOne> evaluatorOneList = evaluatorOneRepository.findAll();
        assertThat(evaluatorOneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluatorOne.class);
        EvaluatorOne evaluatorOne1 = new EvaluatorOne();
        evaluatorOne1.setId(1L);
        EvaluatorOne evaluatorOne2 = new EvaluatorOne();
        evaluatorOne2.setId(evaluatorOne1.getId());
        assertThat(evaluatorOne1).isEqualTo(evaluatorOne2);
        evaluatorOne2.setId(2L);
        assertThat(evaluatorOne1).isNotEqualTo(evaluatorOne2);
        evaluatorOne1.setId(null);
        assertThat(evaluatorOne1).isNotEqualTo(evaluatorOne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluatorOneDTO.class);
        EvaluatorOneDTO evaluatorOneDTO1 = new EvaluatorOneDTO();
        evaluatorOneDTO1.setId(1L);
        EvaluatorOneDTO evaluatorOneDTO2 = new EvaluatorOneDTO();
        assertThat(evaluatorOneDTO1).isNotEqualTo(evaluatorOneDTO2);
        evaluatorOneDTO2.setId(evaluatorOneDTO1.getId());
        assertThat(evaluatorOneDTO1).isEqualTo(evaluatorOneDTO2);
        evaluatorOneDTO2.setId(2L);
        assertThat(evaluatorOneDTO1).isNotEqualTo(evaluatorOneDTO2);
        evaluatorOneDTO1.setId(null);
        assertThat(evaluatorOneDTO1).isNotEqualTo(evaluatorOneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(evaluatorOneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(evaluatorOneMapper.fromId(null)).isNull();
    }
}
