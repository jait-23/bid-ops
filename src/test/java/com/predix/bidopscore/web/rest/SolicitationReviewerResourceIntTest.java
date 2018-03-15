package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.SolicitationReviewer;
import com.predix.bidopscore.repository.SolicitationReviewerRepository;
import com.predix.bidopscore.service.SolicitationReviewerService;
import com.predix.bidopscore.service.dto.SolicitationReviewerDTO;
import com.predix.bidopscore.service.mapper.SolicitationReviewerMapper;
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
 * Test class for the SolicitationReviewerResource REST controller.
 *
 * @see SolicitationReviewerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class SolicitationReviewerResourceIntTest {

    private static final Long DEFAULT_REVIEWER_ID = 1L;
    private static final Long UPDATED_REVIEWER_ID = 2L;

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    @Autowired
    private SolicitationReviewerRepository solicitationReviewerRepository;

    @Autowired
    private SolicitationReviewerMapper solicitationReviewerMapper;

    @Autowired
    private SolicitationReviewerService solicitationReviewerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolicitationReviewerMockMvc;

    private SolicitationReviewer solicitationReviewer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolicitationReviewerResource solicitationReviewerResource = new SolicitationReviewerResource(solicitationReviewerService);
        this.restSolicitationReviewerMockMvc = MockMvcBuilders.standaloneSetup(solicitationReviewerResource)
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
    public static SolicitationReviewer createEntity(EntityManager em) {
        SolicitationReviewer solicitationReviewer = new SolicitationReviewer()
            .reviewerId(DEFAULT_REVIEWER_ID)
            .solicitationId(DEFAULT_SOLICITATION_ID);
        return solicitationReviewer;
    }

    @Before
    public void initTest() {
        solicitationReviewer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitationReviewer() throws Exception {
        int databaseSizeBeforeCreate = solicitationReviewerRepository.findAll().size();

        // Create the SolicitationReviewer
        SolicitationReviewerDTO solicitationReviewerDTO = solicitationReviewerMapper.toDto(solicitationReviewer);
        restSolicitationReviewerMockMvc.perform(post("/api/solicitation-reviewers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationReviewerDTO)))
            .andExpect(status().isCreated());

        // Validate the SolicitationReviewer in the database
        List<SolicitationReviewer> solicitationReviewerList = solicitationReviewerRepository.findAll();
        assertThat(solicitationReviewerList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitationReviewer testSolicitationReviewer = solicitationReviewerList.get(solicitationReviewerList.size() - 1);
        assertThat(testSolicitationReviewer.getReviewerId()).isEqualTo(DEFAULT_REVIEWER_ID);
        assertThat(testSolicitationReviewer.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void createSolicitationReviewerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitationReviewerRepository.findAll().size();

        // Create the SolicitationReviewer with an existing ID
        solicitationReviewer.setId(1L);
        SolicitationReviewerDTO solicitationReviewerDTO = solicitationReviewerMapper.toDto(solicitationReviewer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitationReviewerMockMvc.perform(post("/api/solicitation-reviewers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationReviewerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitationReviewer in the database
        List<SolicitationReviewer> solicitationReviewerList = solicitationReviewerRepository.findAll();
        assertThat(solicitationReviewerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSolicitationReviewers() throws Exception {
        // Initialize the database
        solicitationReviewerRepository.saveAndFlush(solicitationReviewer);

        // Get all the solicitationReviewerList
        restSolicitationReviewerMockMvc.perform(get("/api/solicitation-reviewers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitationReviewer.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewerId").value(hasItem(DEFAULT_REVIEWER_ID.intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSolicitationReviewer() throws Exception {
        // Initialize the database
        solicitationReviewerRepository.saveAndFlush(solicitationReviewer);

        // Get the solicitationReviewer
        restSolicitationReviewerMockMvc.perform(get("/api/solicitation-reviewers/{id}", solicitationReviewer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solicitationReviewer.getId().intValue()))
            .andExpect(jsonPath("$.reviewerId").value(DEFAULT_REVIEWER_ID.intValue()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSolicitationReviewer() throws Exception {
        // Get the solicitationReviewer
        restSolicitationReviewerMockMvc.perform(get("/api/solicitation-reviewers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitationReviewer() throws Exception {
        // Initialize the database
        solicitationReviewerRepository.saveAndFlush(solicitationReviewer);
        int databaseSizeBeforeUpdate = solicitationReviewerRepository.findAll().size();

        // Update the solicitationReviewer
        SolicitationReviewer updatedSolicitationReviewer = solicitationReviewerRepository.findOne(solicitationReviewer.getId());
        // Disconnect from session so that the updates on updatedSolicitationReviewer are not directly saved in db
        em.detach(updatedSolicitationReviewer);
        updatedSolicitationReviewer
            .reviewerId(UPDATED_REVIEWER_ID)
            .solicitationId(UPDATED_SOLICITATION_ID);
        SolicitationReviewerDTO solicitationReviewerDTO = solicitationReviewerMapper.toDto(updatedSolicitationReviewer);

        restSolicitationReviewerMockMvc.perform(put("/api/solicitation-reviewers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationReviewerDTO)))
            .andExpect(status().isOk());

        // Validate the SolicitationReviewer in the database
        List<SolicitationReviewer> solicitationReviewerList = solicitationReviewerRepository.findAll();
        assertThat(solicitationReviewerList).hasSize(databaseSizeBeforeUpdate);
        SolicitationReviewer testSolicitationReviewer = solicitationReviewerList.get(solicitationReviewerList.size() - 1);
        assertThat(testSolicitationReviewer.getReviewerId()).isEqualTo(UPDATED_REVIEWER_ID);
        assertThat(testSolicitationReviewer.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitationReviewer() throws Exception {
        int databaseSizeBeforeUpdate = solicitationReviewerRepository.findAll().size();

        // Create the SolicitationReviewer
        SolicitationReviewerDTO solicitationReviewerDTO = solicitationReviewerMapper.toDto(solicitationReviewer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolicitationReviewerMockMvc.perform(put("/api/solicitation-reviewers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationReviewerDTO)))
            .andExpect(status().isCreated());

        // Validate the SolicitationReviewer in the database
        List<SolicitationReviewer> solicitationReviewerList = solicitationReviewerRepository.findAll();
        assertThat(solicitationReviewerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolicitationReviewer() throws Exception {
        // Initialize the database
        solicitationReviewerRepository.saveAndFlush(solicitationReviewer);
        int databaseSizeBeforeDelete = solicitationReviewerRepository.findAll().size();

        // Get the solicitationReviewer
        restSolicitationReviewerMockMvc.perform(delete("/api/solicitation-reviewers/{id}", solicitationReviewer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SolicitationReviewer> solicitationReviewerList = solicitationReviewerRepository.findAll();
        assertThat(solicitationReviewerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitationReviewer.class);
        SolicitationReviewer solicitationReviewer1 = new SolicitationReviewer();
        solicitationReviewer1.setId(1L);
        SolicitationReviewer solicitationReviewer2 = new SolicitationReviewer();
        solicitationReviewer2.setId(solicitationReviewer1.getId());
        assertThat(solicitationReviewer1).isEqualTo(solicitationReviewer2);
        solicitationReviewer2.setId(2L);
        assertThat(solicitationReviewer1).isNotEqualTo(solicitationReviewer2);
        solicitationReviewer1.setId(null);
        assertThat(solicitationReviewer1).isNotEqualTo(solicitationReviewer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitationReviewerDTO.class);
        SolicitationReviewerDTO solicitationReviewerDTO1 = new SolicitationReviewerDTO();
        solicitationReviewerDTO1.setId(1L);
        SolicitationReviewerDTO solicitationReviewerDTO2 = new SolicitationReviewerDTO();
        assertThat(solicitationReviewerDTO1).isNotEqualTo(solicitationReviewerDTO2);
        solicitationReviewerDTO2.setId(solicitationReviewerDTO1.getId());
        assertThat(solicitationReviewerDTO1).isEqualTo(solicitationReviewerDTO2);
        solicitationReviewerDTO2.setId(2L);
        assertThat(solicitationReviewerDTO1).isNotEqualTo(solicitationReviewerDTO2);
        solicitationReviewerDTO1.setId(null);
        assertThat(solicitationReviewerDTO1).isNotEqualTo(solicitationReviewerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(solicitationReviewerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(solicitationReviewerMapper.fromId(null)).isNull();
    }
}
