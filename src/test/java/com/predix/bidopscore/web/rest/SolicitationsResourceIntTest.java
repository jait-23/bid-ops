package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.Solicitations;
import com.predix.bidopscore.repository.SolicitationsRepository;
import com.predix.bidopscore.service.SolicitationsService;
import com.predix.bidopscore.service.dto.SolicitationsDTO;
import com.predix.bidopscore.service.mapper.SolicitationsMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.predix.bidopscore.web.rest.TestUtil.sameInstant;
import static com.predix.bidopscore.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SolicitationsResource REST controller.
 *
 * @see SolicitationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class SolicitationsResourceIntTest {

    private static final String DEFAULT_SOLICITATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOLICITATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FINAL_FILING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINAL_FILING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIRED_DOCUMENTS = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRED_DOCUMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_REVIEWER_DELIVERY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWER_DELIVERY_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_APPROVER_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final Long UPDATED_AUTHOR_ID = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private SolicitationsRepository solicitationsRepository;

    @Autowired
    private SolicitationsMapper solicitationsMapper;

    @Autowired
    private SolicitationsService solicitationsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolicitationsMockMvc;

    private Solicitations solicitations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolicitationsResource solicitationsResource = new SolicitationsResource(solicitationsService);
        this.restSolicitationsMockMvc = MockMvcBuilders.standaloneSetup(solicitationsResource)
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
    public static Solicitations createEntity(EntityManager em) {
        Solicitations solicitations = new Solicitations()
            .solicitationId(DEFAULT_SOLICITATION_ID)
            .title(DEFAULT_TITLE)
            .status(DEFAULT_STATUS)
            .finalFilingDate(DEFAULT_FINAL_FILING_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .category(DEFAULT_CATEGORY)
            .requiredDocuments(DEFAULT_REQUIRED_DOCUMENTS)
            .reviewerDeliveryStatus(DEFAULT_REVIEWER_DELIVERY_STATUS)
            .approverStatus(DEFAULT_APPROVER_STATUS)
            .authorId(DEFAULT_AUTHOR_ID)
            .userId(DEFAULT_USER_ID);
        return solicitations;
    }

    @Before
    public void initTest() {
        solicitations = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitations() throws Exception {
        int databaseSizeBeforeCreate = solicitationsRepository.findAll().size();

        // Create the Solicitations
        SolicitationsDTO solicitationsDTO = solicitationsMapper.toDto(solicitations);
        restSolicitationsMockMvc.perform(post("/api/solicitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Solicitations in the database
        List<Solicitations> solicitationsList = solicitationsRepository.findAll();
        assertThat(solicitationsList).hasSize(databaseSizeBeforeCreate + 1);
        Solicitations testSolicitations = solicitationsList.get(solicitationsList.size() - 1);
        assertThat(testSolicitations.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
        assertThat(testSolicitations.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSolicitations.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSolicitations.getFinalFilingDate()).isEqualTo(DEFAULT_FINAL_FILING_DATE);
        assertThat(testSolicitations.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testSolicitations.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSolicitations.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSolicitations.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSolicitations.getRequiredDocuments()).isEqualTo(DEFAULT_REQUIRED_DOCUMENTS);
        assertThat(testSolicitations.getReviewerDeliveryStatus()).isEqualTo(DEFAULT_REVIEWER_DELIVERY_STATUS);
        assertThat(testSolicitations.getApproverStatus()).isEqualTo(DEFAULT_APPROVER_STATUS);
        assertThat(testSolicitations.getAuthorId()).isEqualTo(DEFAULT_AUTHOR_ID);
        assertThat(testSolicitations.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createSolicitationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitationsRepository.findAll().size();

        // Create the Solicitations with an existing ID
        solicitations.setId(1L);
        SolicitationsDTO solicitationsDTO = solicitationsMapper.toDto(solicitations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitationsMockMvc.perform(post("/api/solicitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Solicitations in the database
        List<Solicitations> solicitationsList = solicitationsRepository.findAll();
        assertThat(solicitationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSolicitations() throws Exception {
        // Initialize the database
        solicitationsRepository.saveAndFlush(solicitations);

        // Get all the solicitationsList
        restSolicitationsMockMvc.perform(get("/api/solicitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitations.getId().intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].finalFilingDate").value(hasItem(sameInstant(DEFAULT_FINAL_FILING_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].requiredDocuments").value(hasItem(DEFAULT_REQUIRED_DOCUMENTS.toString())))
            .andExpect(jsonPath("$.[*].reviewerDeliveryStatus").value(hasItem(DEFAULT_REVIEWER_DELIVERY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].approverStatus").value(hasItem(DEFAULT_APPROVER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].authorId").value(hasItem(DEFAULT_AUTHOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSolicitations() throws Exception {
        // Initialize the database
        solicitationsRepository.saveAndFlush(solicitations);

        // Get the solicitations
        restSolicitationsMockMvc.perform(get("/api/solicitations/{id}", solicitations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solicitations.getId().intValue()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.finalFilingDate").value(sameInstant(DEFAULT_FINAL_FILING_DATE)))
            .andExpect(jsonPath("$.lastUpdated").value(sameInstant(DEFAULT_LAST_UPDATED)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.requiredDocuments").value(DEFAULT_REQUIRED_DOCUMENTS.toString()))
            .andExpect(jsonPath("$.reviewerDeliveryStatus").value(DEFAULT_REVIEWER_DELIVERY_STATUS.toString()))
            .andExpect(jsonPath("$.approverStatus").value(DEFAULT_APPROVER_STATUS.toString()))
            .andExpect(jsonPath("$.authorId").value(DEFAULT_AUTHOR_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSolicitations() throws Exception {
        // Get the solicitations
        restSolicitationsMockMvc.perform(get("/api/solicitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitations() throws Exception {
        // Initialize the database
        solicitationsRepository.saveAndFlush(solicitations);
        int databaseSizeBeforeUpdate = solicitationsRepository.findAll().size();

        // Update the solicitations
        Solicitations updatedSolicitations = solicitationsRepository.findOne(solicitations.getId());
        // Disconnect from session so that the updates on updatedSolicitations are not directly saved in db
        em.detach(updatedSolicitations);
        updatedSolicitations
            .solicitationId(UPDATED_SOLICITATION_ID)
            .title(UPDATED_TITLE)
            .status(UPDATED_STATUS)
            .finalFilingDate(UPDATED_FINAL_FILING_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .requiredDocuments(UPDATED_REQUIRED_DOCUMENTS)
            .reviewerDeliveryStatus(UPDATED_REVIEWER_DELIVERY_STATUS)
            .approverStatus(UPDATED_APPROVER_STATUS)
            .authorId(UPDATED_AUTHOR_ID)
            .userId(UPDATED_USER_ID);
        SolicitationsDTO solicitationsDTO = solicitationsMapper.toDto(updatedSolicitations);

        restSolicitationsMockMvc.perform(put("/api/solicitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationsDTO)))
            .andExpect(status().isOk());

        // Validate the Solicitations in the database
        List<Solicitations> solicitationsList = solicitationsRepository.findAll();
        assertThat(solicitationsList).hasSize(databaseSizeBeforeUpdate);
        Solicitations testSolicitations = solicitationsList.get(solicitationsList.size() - 1);
        assertThat(testSolicitations.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
        assertThat(testSolicitations.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSolicitations.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSolicitations.getFinalFilingDate()).isEqualTo(UPDATED_FINAL_FILING_DATE);
        assertThat(testSolicitations.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testSolicitations.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSolicitations.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSolicitations.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSolicitations.getRequiredDocuments()).isEqualTo(UPDATED_REQUIRED_DOCUMENTS);
        assertThat(testSolicitations.getReviewerDeliveryStatus()).isEqualTo(UPDATED_REVIEWER_DELIVERY_STATUS);
        assertThat(testSolicitations.getApproverStatus()).isEqualTo(UPDATED_APPROVER_STATUS);
        assertThat(testSolicitations.getAuthorId()).isEqualTo(UPDATED_AUTHOR_ID);
        assertThat(testSolicitations.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitations() throws Exception {
        int databaseSizeBeforeUpdate = solicitationsRepository.findAll().size();

        // Create the Solicitations
        SolicitationsDTO solicitationsDTO = solicitationsMapper.toDto(solicitations);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolicitationsMockMvc.perform(put("/api/solicitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Solicitations in the database
        List<Solicitations> solicitationsList = solicitationsRepository.findAll();
        assertThat(solicitationsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolicitations() throws Exception {
        // Initialize the database
        solicitationsRepository.saveAndFlush(solicitations);
        int databaseSizeBeforeDelete = solicitationsRepository.findAll().size();

        // Get the solicitations
        restSolicitationsMockMvc.perform(delete("/api/solicitations/{id}", solicitations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Solicitations> solicitationsList = solicitationsRepository.findAll();
        assertThat(solicitationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solicitations.class);
        Solicitations solicitations1 = new Solicitations();
        solicitations1.setId(1L);
        Solicitations solicitations2 = new Solicitations();
        solicitations2.setId(solicitations1.getId());
        assertThat(solicitations1).isEqualTo(solicitations2);
        solicitations2.setId(2L);
        assertThat(solicitations1).isNotEqualTo(solicitations2);
        solicitations1.setId(null);
        assertThat(solicitations1).isNotEqualTo(solicitations2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitationsDTO.class);
        SolicitationsDTO solicitationsDTO1 = new SolicitationsDTO();
        solicitationsDTO1.setId(1L);
        SolicitationsDTO solicitationsDTO2 = new SolicitationsDTO();
        assertThat(solicitationsDTO1).isNotEqualTo(solicitationsDTO2);
        solicitationsDTO2.setId(solicitationsDTO1.getId());
        assertThat(solicitationsDTO1).isEqualTo(solicitationsDTO2);
        solicitationsDTO2.setId(2L);
        assertThat(solicitationsDTO1).isNotEqualTo(solicitationsDTO2);
        solicitationsDTO1.setId(null);
        assertThat(solicitationsDTO1).isNotEqualTo(solicitationsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(solicitationsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(solicitationsMapper.fromId(null)).isNull();
    }
}
