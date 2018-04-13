package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.BiddersSolicitationsSubmitted;
import com.predix.bidopscore.repository.BiddersSolicitationsSubmittedRepository;
import com.predix.bidopscore.service.BiddersSolicitationsSubmittedService;
import com.predix.bidopscore.service.dto.BiddersSolicitationsSubmittedDTO;
import com.predix.bidopscore.service.mapper.BiddersSolicitationsSubmittedMapper;
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
 * Test class for the BiddersSolicitationsSubmittedResource REST controller.
 *
 * @see BiddersSolicitationsSubmittedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class BiddersSolicitationsSubmittedResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    private static final String DEFAULT_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_DOC_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_SOLICITATIONS = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_SOLICITATIONS = "BBBBBBBBBB";

    @Autowired
    private BiddersSolicitationsSubmittedRepository biddersSolicitationsSubmittedRepository;

    @Autowired
    private BiddersSolicitationsSubmittedMapper biddersSolicitationsSubmittedMapper;

    @Autowired
    private BiddersSolicitationsSubmittedService biddersSolicitationsSubmittedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBiddersSolicitationsSubmittedMockMvc;

    private BiddersSolicitationsSubmitted biddersSolicitationsSubmitted;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BiddersSolicitationsSubmittedResource biddersSolicitationsSubmittedResource = new BiddersSolicitationsSubmittedResource(biddersSolicitationsSubmittedService);
        this.restBiddersSolicitationsSubmittedMockMvc = MockMvcBuilders.standaloneSetup(biddersSolicitationsSubmittedResource)
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
    public static BiddersSolicitationsSubmitted createEntity(EntityManager em) {
        BiddersSolicitationsSubmitted biddersSolicitationsSubmitted = new BiddersSolicitationsSubmitted()
            .userId(DEFAULT_USER_ID)
            .solicitationId(DEFAULT_SOLICITATION_ID)
            .docURL(DEFAULT_DOC_URL)
            .categorySolicitations(DEFAULT_CATEGORY_SOLICITATIONS);
        return biddersSolicitationsSubmitted;
    }

    @Before
    public void initTest() {
        biddersSolicitationsSubmitted = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiddersSolicitationsSubmitted() throws Exception {
        int databaseSizeBeforeCreate = biddersSolicitationsSubmittedRepository.findAll().size();

        // Create the BiddersSolicitationsSubmitted
        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO = biddersSolicitationsSubmittedMapper.toDto(biddersSolicitationsSubmitted);
        restBiddersSolicitationsSubmittedMockMvc.perform(post("/api/bidders-solicitations-submitteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersSolicitationsSubmittedDTO)))
            .andExpect(status().isCreated());

        // Validate the BiddersSolicitationsSubmitted in the database
        List<BiddersSolicitationsSubmitted> biddersSolicitationsSubmittedList = biddersSolicitationsSubmittedRepository.findAll();
        assertThat(biddersSolicitationsSubmittedList).hasSize(databaseSizeBeforeCreate + 1);
        BiddersSolicitationsSubmitted testBiddersSolicitationsSubmitted = biddersSolicitationsSubmittedList.get(biddersSolicitationsSubmittedList.size() - 1);
        assertThat(testBiddersSolicitationsSubmitted.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBiddersSolicitationsSubmitted.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
        assertThat(testBiddersSolicitationsSubmitted.getDocURL()).isEqualTo(DEFAULT_DOC_URL);
        assertThat(testBiddersSolicitationsSubmitted.getCategorySolicitations()).isEqualTo(DEFAULT_CATEGORY_SOLICITATIONS);
    }

    @Test
    @Transactional
    public void createBiddersSolicitationsSubmittedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biddersSolicitationsSubmittedRepository.findAll().size();

        // Create the BiddersSolicitationsSubmitted with an existing ID
        biddersSolicitationsSubmitted.setId(1L);
        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO = biddersSolicitationsSubmittedMapper.toDto(biddersSolicitationsSubmitted);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiddersSolicitationsSubmittedMockMvc.perform(post("/api/bidders-solicitations-submitteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersSolicitationsSubmittedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BiddersSolicitationsSubmitted in the database
        List<BiddersSolicitationsSubmitted> biddersSolicitationsSubmittedList = biddersSolicitationsSubmittedRepository.findAll();
        assertThat(biddersSolicitationsSubmittedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBiddersSolicitationsSubmitteds() throws Exception {
        // Initialize the database
        biddersSolicitationsSubmittedRepository.saveAndFlush(biddersSolicitationsSubmitted);

        // Get all the biddersSolicitationsSubmittedList
        restBiddersSolicitationsSubmittedMockMvc.perform(get("/api/bidders-solicitations-submitteds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biddersSolicitationsSubmitted.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].docURL").value(hasItem(DEFAULT_DOC_URL.toString())))
            .andExpect(jsonPath("$.[*].categorySolicitations").value(hasItem(DEFAULT_CATEGORY_SOLICITATIONS.toString())));
    }

    @Test
    @Transactional
    public void getBiddersSolicitationsSubmitted() throws Exception {
        // Initialize the database
        biddersSolicitationsSubmittedRepository.saveAndFlush(biddersSolicitationsSubmitted);

        // Get the biddersSolicitationsSubmitted
        restBiddersSolicitationsSubmittedMockMvc.perform(get("/api/bidders-solicitations-submitteds/{id}", biddersSolicitationsSubmitted.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(biddersSolicitationsSubmitted.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()))
            .andExpect(jsonPath("$.docURL").value(DEFAULT_DOC_URL.toString()))
            .andExpect(jsonPath("$.categorySolicitations").value(DEFAULT_CATEGORY_SOLICITATIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBiddersSolicitationsSubmitted() throws Exception {
        // Get the biddersSolicitationsSubmitted
        restBiddersSolicitationsSubmittedMockMvc.perform(get("/api/bidders-solicitations-submitteds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiddersSolicitationsSubmitted() throws Exception {
        // Initialize the database
        biddersSolicitationsSubmittedRepository.saveAndFlush(biddersSolicitationsSubmitted);
        int databaseSizeBeforeUpdate = biddersSolicitationsSubmittedRepository.findAll().size();

        // Update the biddersSolicitationsSubmitted
        BiddersSolicitationsSubmitted updatedBiddersSolicitationsSubmitted = biddersSolicitationsSubmittedRepository.findOne(biddersSolicitationsSubmitted.getId());
        // Disconnect from session so that the updates on updatedBiddersSolicitationsSubmitted are not directly saved in db
        em.detach(updatedBiddersSolicitationsSubmitted);
        updatedBiddersSolicitationsSubmitted
            .userId(UPDATED_USER_ID)
            .solicitationId(UPDATED_SOLICITATION_ID)
            .docURL(UPDATED_DOC_URL)
            .categorySolicitations(UPDATED_CATEGORY_SOLICITATIONS);
        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO = biddersSolicitationsSubmittedMapper.toDto(updatedBiddersSolicitationsSubmitted);

        restBiddersSolicitationsSubmittedMockMvc.perform(put("/api/bidders-solicitations-submitteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersSolicitationsSubmittedDTO)))
            .andExpect(status().isOk());

        // Validate the BiddersSolicitationsSubmitted in the database
        List<BiddersSolicitationsSubmitted> biddersSolicitationsSubmittedList = biddersSolicitationsSubmittedRepository.findAll();
        assertThat(biddersSolicitationsSubmittedList).hasSize(databaseSizeBeforeUpdate);
        BiddersSolicitationsSubmitted testBiddersSolicitationsSubmitted = biddersSolicitationsSubmittedList.get(biddersSolicitationsSubmittedList.size() - 1);
        assertThat(testBiddersSolicitationsSubmitted.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBiddersSolicitationsSubmitted.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
        assertThat(testBiddersSolicitationsSubmitted.getDocURL()).isEqualTo(UPDATED_DOC_URL);
        assertThat(testBiddersSolicitationsSubmitted.getCategorySolicitations()).isEqualTo(UPDATED_CATEGORY_SOLICITATIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingBiddersSolicitationsSubmitted() throws Exception {
        int databaseSizeBeforeUpdate = biddersSolicitationsSubmittedRepository.findAll().size();

        // Create the BiddersSolicitationsSubmitted
        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO = biddersSolicitationsSubmittedMapper.toDto(biddersSolicitationsSubmitted);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBiddersSolicitationsSubmittedMockMvc.perform(put("/api/bidders-solicitations-submitteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersSolicitationsSubmittedDTO)))
            .andExpect(status().isCreated());

        // Validate the BiddersSolicitationsSubmitted in the database
        List<BiddersSolicitationsSubmitted> biddersSolicitationsSubmittedList = biddersSolicitationsSubmittedRepository.findAll();
        assertThat(biddersSolicitationsSubmittedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBiddersSolicitationsSubmitted() throws Exception {
        // Initialize the database
        biddersSolicitationsSubmittedRepository.saveAndFlush(biddersSolicitationsSubmitted);
        int databaseSizeBeforeDelete = biddersSolicitationsSubmittedRepository.findAll().size();

        // Get the biddersSolicitationsSubmitted
        restBiddersSolicitationsSubmittedMockMvc.perform(delete("/api/bidders-solicitations-submitteds/{id}", biddersSolicitationsSubmitted.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BiddersSolicitationsSubmitted> biddersSolicitationsSubmittedList = biddersSolicitationsSubmittedRepository.findAll();
        assertThat(biddersSolicitationsSubmittedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiddersSolicitationsSubmitted.class);
        BiddersSolicitationsSubmitted biddersSolicitationsSubmitted1 = new BiddersSolicitationsSubmitted();
        biddersSolicitationsSubmitted1.setId(1L);
        BiddersSolicitationsSubmitted biddersSolicitationsSubmitted2 = new BiddersSolicitationsSubmitted();
        biddersSolicitationsSubmitted2.setId(biddersSolicitationsSubmitted1.getId());
        assertThat(biddersSolicitationsSubmitted1).isEqualTo(biddersSolicitationsSubmitted2);
        biddersSolicitationsSubmitted2.setId(2L);
        assertThat(biddersSolicitationsSubmitted1).isNotEqualTo(biddersSolicitationsSubmitted2);
        biddersSolicitationsSubmitted1.setId(null);
        assertThat(biddersSolicitationsSubmitted1).isNotEqualTo(biddersSolicitationsSubmitted2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiddersSolicitationsSubmittedDTO.class);
        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO1 = new BiddersSolicitationsSubmittedDTO();
        biddersSolicitationsSubmittedDTO1.setId(1L);
        BiddersSolicitationsSubmittedDTO biddersSolicitationsSubmittedDTO2 = new BiddersSolicitationsSubmittedDTO();
        assertThat(biddersSolicitationsSubmittedDTO1).isNotEqualTo(biddersSolicitationsSubmittedDTO2);
        biddersSolicitationsSubmittedDTO2.setId(biddersSolicitationsSubmittedDTO1.getId());
        assertThat(biddersSolicitationsSubmittedDTO1).isEqualTo(biddersSolicitationsSubmittedDTO2);
        biddersSolicitationsSubmittedDTO2.setId(2L);
        assertThat(biddersSolicitationsSubmittedDTO1).isNotEqualTo(biddersSolicitationsSubmittedDTO2);
        biddersSolicitationsSubmittedDTO1.setId(null);
        assertThat(biddersSolicitationsSubmittedDTO1).isNotEqualTo(biddersSolicitationsSubmittedDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(biddersSolicitationsSubmittedMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(biddersSolicitationsSubmittedMapper.fromId(null)).isNull();
    }
}
