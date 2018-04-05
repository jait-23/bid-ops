package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.Bidders;
import com.predix.bidopscore.repository.BiddersRepository;
import com.predix.bidopscore.service.BiddersService;
import com.predix.bidopscore.service.dto.BiddersDTO;
import com.predix.bidopscore.service.mapper.BiddersMapper;
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
 * Test class for the BiddersResource REST controller.
 *
 * @see BiddersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class BiddersResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BID_CATEGORIES = "AAAAAAAAAA";
    private static final String UPDATED_BID_CATEGORIES = "BBBBBBBBBB";

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    @Autowired
    private BiddersRepository biddersRepository;

    @Autowired
    private BiddersMapper biddersMapper;

    @Autowired
    private BiddersService biddersService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBiddersMockMvc;

    private Bidders bidders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BiddersResource biddersResource = new BiddersResource(biddersService);
        this.restBiddersMockMvc = MockMvcBuilders.standaloneSetup(biddersResource)
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
    public static Bidders createEntity(EntityManager em) {
        Bidders bidders = new Bidders()
            .name(DEFAULT_NAME)
            .bidCategories(DEFAULT_BID_CATEGORIES)
            .solicitationId(DEFAULT_SOLICITATION_ID);
        return bidders;
    }

    @Before
    public void initTest() {
        bidders = createEntity(em);
    }

    @Test
    @Transactional
    public void createBidders() throws Exception {
        int databaseSizeBeforeCreate = biddersRepository.findAll().size();

        // Create the Bidders
        BiddersDTO biddersDTO = biddersMapper.toDto(bidders);
        restBiddersMockMvc.perform(post("/api/bidders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersDTO)))
            .andExpect(status().isCreated());

        // Validate the Bidders in the database
        List<Bidders> biddersList = biddersRepository.findAll();
        assertThat(biddersList).hasSize(databaseSizeBeforeCreate + 1);
        Bidders testBidders = biddersList.get(biddersList.size() - 1);
        assertThat(testBidders.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBidders.getBidCategories()).isEqualTo(DEFAULT_BID_CATEGORIES);
        assertThat(testBidders.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void createBiddersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biddersRepository.findAll().size();

        // Create the Bidders with an existing ID
        bidders.setId(1L);
        BiddersDTO biddersDTO = biddersMapper.toDto(bidders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiddersMockMvc.perform(post("/api/bidders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bidders in the database
        List<Bidders> biddersList = biddersRepository.findAll();
        assertThat(biddersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBidders() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList
        restBiddersMockMvc.perform(get("/api/bidders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bidders.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].bidCategories").value(hasItem(DEFAULT_BID_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBidders() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get the bidders
        restBiddersMockMvc.perform(get("/api/bidders/{id}", bidders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bidders.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.bidCategories").value(DEFAULT_BID_CATEGORIES.toString()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBidders() throws Exception {
        // Get the bidders
        restBiddersMockMvc.perform(get("/api/bidders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBidders() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);
        int databaseSizeBeforeUpdate = biddersRepository.findAll().size();

        // Update the bidders
        Bidders updatedBidders = biddersRepository.findOne(bidders.getId());
        // Disconnect from session so that the updates on updatedBidders are not directly saved in db
        em.detach(updatedBidders);
        updatedBidders
            .name(UPDATED_NAME)
            .bidCategories(UPDATED_BID_CATEGORIES)
            .solicitationId(UPDATED_SOLICITATION_ID);
        BiddersDTO biddersDTO = biddersMapper.toDto(updatedBidders);

        restBiddersMockMvc.perform(put("/api/bidders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersDTO)))
            .andExpect(status().isOk());

        // Validate the Bidders in the database
        List<Bidders> biddersList = biddersRepository.findAll();
        assertThat(biddersList).hasSize(databaseSizeBeforeUpdate);
        Bidders testBidders = biddersList.get(biddersList.size() - 1);
        assertThat(testBidders.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBidders.getBidCategories()).isEqualTo(UPDATED_BID_CATEGORIES);
        assertThat(testBidders.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBidders() throws Exception {
        int databaseSizeBeforeUpdate = biddersRepository.findAll().size();

        // Create the Bidders
        BiddersDTO biddersDTO = biddersMapper.toDto(bidders);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBiddersMockMvc.perform(put("/api/bidders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersDTO)))
            .andExpect(status().isCreated());

        // Validate the Bidders in the database
        List<Bidders> biddersList = biddersRepository.findAll();
        assertThat(biddersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBidders() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);
        int databaseSizeBeforeDelete = biddersRepository.findAll().size();

        // Get the bidders
        restBiddersMockMvc.perform(delete("/api/bidders/{id}", bidders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bidders> biddersList = biddersRepository.findAll();
        assertThat(biddersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bidders.class);
        Bidders bidders1 = new Bidders();
        bidders1.setId(1L);
        Bidders bidders2 = new Bidders();
        bidders2.setId(bidders1.getId());
        assertThat(bidders1).isEqualTo(bidders2);
        bidders2.setId(2L);
        assertThat(bidders1).isNotEqualTo(bidders2);
        bidders1.setId(null);
        assertThat(bidders1).isNotEqualTo(bidders2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiddersDTO.class);
        BiddersDTO biddersDTO1 = new BiddersDTO();
        biddersDTO1.setId(1L);
        BiddersDTO biddersDTO2 = new BiddersDTO();
        assertThat(biddersDTO1).isNotEqualTo(biddersDTO2);
        biddersDTO2.setId(biddersDTO1.getId());
        assertThat(biddersDTO1).isEqualTo(biddersDTO2);
        biddersDTO2.setId(2L);
        assertThat(biddersDTO1).isNotEqualTo(biddersDTO2);
        biddersDTO1.setId(null);
        assertThat(biddersDTO1).isNotEqualTo(biddersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(biddersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(biddersMapper.fromId(null)).isNull();
    }
}
