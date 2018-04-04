package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.Bidders;
import com.predix.bidopscore.domain.Solicitations;
import com.predix.bidopscore.domain.Jhi_user;
import com.predix.bidopscore.domain.Solicitations;
import com.predix.bidopscore.repository.BiddersRepository;
import com.predix.bidopscore.service.BiddersService;
import com.predix.bidopscore.service.dto.BiddersDTO;
import com.predix.bidopscore.service.mapper.BiddersMapper;
import com.predix.bidopscore.web.rest.errors.ExceptionTranslator;
import com.predix.bidopscore.service.dto.BiddersCriteria;
import com.predix.bidopscore.service.BiddersQueryService1;

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

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BID_CATEGORIES = "AAAAAAAAAA";
    private static final String UPDATED_BID_CATEGORIES = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private BiddersRepository biddersRepository;

    @Autowired
    private BiddersMapper biddersMapper;

    @Autowired
    private BiddersService biddersService;

    @Autowired
    private BiddersQueryService1 biddersQueryService;

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
        final BiddersResource biddersResource = new BiddersResource(biddersService, biddersQueryService);
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
            .solicitationId(DEFAULT_SOLICITATION_ID)
            .name(DEFAULT_NAME)
            .bidCategories(DEFAULT_BID_CATEGORIES)
            .userId(DEFAULT_USER_ID);
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
        assertThat(testBidders.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
        assertThat(testBidders.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBidders.getBidCategories()).isEqualTo(DEFAULT_BID_CATEGORIES);
        assertThat(testBidders.getUserId()).isEqualTo(DEFAULT_USER_ID);
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
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].bidCategories").value(hasItem(DEFAULT_BID_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
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
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.bidCategories").value(DEFAULT_BID_CATEGORIES.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllBiddersBySolicitationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where solicitationId equals to DEFAULT_SOLICITATION_ID
        defaultBiddersShouldBeFound("solicitationId.equals=" + DEFAULT_SOLICITATION_ID);

        // Get all the biddersList where solicitationId equals to UPDATED_SOLICITATION_ID
        defaultBiddersShouldNotBeFound("solicitationId.equals=" + UPDATED_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void getAllBiddersBySolicitationIdIsInShouldWork() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where solicitationId in DEFAULT_SOLICITATION_ID or UPDATED_SOLICITATION_ID
        defaultBiddersShouldBeFound("solicitationId.in=" + DEFAULT_SOLICITATION_ID + "," + UPDATED_SOLICITATION_ID);

        // Get all the biddersList where solicitationId equals to UPDATED_SOLICITATION_ID
        defaultBiddersShouldNotBeFound("solicitationId.in=" + UPDATED_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void getAllBiddersBySolicitationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where solicitationId is not null
        defaultBiddersShouldBeFound("solicitationId.specified=true");

        // Get all the biddersList where solicitationId is null
        defaultBiddersShouldNotBeFound("solicitationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllBiddersBySolicitationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where solicitationId greater than or equals to DEFAULT_SOLICITATION_ID
        defaultBiddersShouldBeFound("solicitationId.greaterOrEqualThan=" + DEFAULT_SOLICITATION_ID);

        // Get all the biddersList where solicitationId greater than or equals to UPDATED_SOLICITATION_ID
        defaultBiddersShouldNotBeFound("solicitationId.greaterOrEqualThan=" + UPDATED_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void getAllBiddersBySolicitationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where solicitationId less than or equals to DEFAULT_SOLICITATION_ID
        defaultBiddersShouldNotBeFound("solicitationId.lessThan=" + DEFAULT_SOLICITATION_ID);

        // Get all the biddersList where solicitationId less than or equals to UPDATED_SOLICITATION_ID
        defaultBiddersShouldBeFound("solicitationId.lessThan=" + UPDATED_SOLICITATION_ID);
    }


    @Test
    @Transactional
    public void getAllBiddersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where name equals to DEFAULT_NAME
        defaultBiddersShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the biddersList where name equals to UPDATED_NAME
        defaultBiddersShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBiddersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBiddersShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the biddersList where name equals to UPDATED_NAME
        defaultBiddersShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBiddersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where name is not null
        defaultBiddersShouldBeFound("name.specified=true");

        // Get all the biddersList where name is null
        defaultBiddersShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllBiddersByBidCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where bidCategories equals to DEFAULT_BID_CATEGORIES
        defaultBiddersShouldBeFound("bidCategories.equals=" + DEFAULT_BID_CATEGORIES);

        // Get all the biddersList where bidCategories equals to UPDATED_BID_CATEGORIES
        defaultBiddersShouldNotBeFound("bidCategories.equals=" + UPDATED_BID_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllBiddersByBidCategoriesIsInShouldWork() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where bidCategories in DEFAULT_BID_CATEGORIES or UPDATED_BID_CATEGORIES
        defaultBiddersShouldBeFound("bidCategories.in=" + DEFAULT_BID_CATEGORIES + "," + UPDATED_BID_CATEGORIES);

        // Get all the biddersList where bidCategories equals to UPDATED_BID_CATEGORIES
        defaultBiddersShouldNotBeFound("bidCategories.in=" + UPDATED_BID_CATEGORIES);
    }

    @Test
    @Transactional
    public void getAllBiddersByBidCategoriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where bidCategories is not null
        defaultBiddersShouldBeFound("bidCategories.specified=true");

        // Get all the biddersList where bidCategories is null
        defaultBiddersShouldNotBeFound("bidCategories.specified=false");
    }

    @Test
    @Transactional
    public void getAllBiddersByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where userId equals to DEFAULT_USER_ID
        defaultBiddersShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the biddersList where userId equals to UPDATED_USER_ID
        defaultBiddersShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllBiddersByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultBiddersShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the biddersList where userId equals to UPDATED_USER_ID
        defaultBiddersShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllBiddersByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where userId is not null
        defaultBiddersShouldBeFound("userId.specified=true");

        // Get all the biddersList where userId is null
        defaultBiddersShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllBiddersByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where userId greater than or equals to DEFAULT_USER_ID
        defaultBiddersShouldBeFound("userId.greaterOrEqualThan=" + DEFAULT_USER_ID);

        // Get all the biddersList where userId greater than or equals to UPDATED_USER_ID
        defaultBiddersShouldNotBeFound("userId.greaterOrEqualThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllBiddersByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        biddersRepository.saveAndFlush(bidders);

        // Get all the biddersList where userId less than or equals to DEFAULT_USER_ID
        defaultBiddersShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the biddersList where userId less than or equals to UPDATED_USER_ID
        defaultBiddersShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }


    @Test
    @Transactional
    public void getAllBiddersByBiddersIsEqualToSomething() throws Exception {
        // Initialize the database
        Solicitations bidders = SolicitationsResourceIntTest.createEntity(em);
        em.persist(bidders);
        em.flush();
        bidders.setBidders(bidders);
        bidders.setBidders(bidders);
        biddersRepository.saveAndFlush(bidders);
        Long biddersId = bidders.getId();

        // Get all the biddersList where bidders equals to biddersId
        defaultBiddersShouldBeFound("biddersId.equals=" + biddersId);

        // Get all the biddersList where bidders equals to biddersId + 1
        defaultBiddersShouldNotBeFound("biddersId.equals=" + (biddersId + 1));
    }


    @Test
    @Transactional
    public void getAllBiddersByJhi_userIsEqualToSomething() throws Exception {
        // Initialize the database
        Jhi_user jhi_user = Jhi_userResourceIntTest.createEntity(em);
        em.persist(jhi_user);
        em.flush();
        bidders.setJhi_user(jhi_user);
        biddersRepository.saveAndFlush(bidders);
        Long jhi_userId = jhi_user.getId();

        // Get all the biddersList where jhi_user equals to jhi_userId
        defaultBiddersShouldBeFound("jhi_userId.equals=" + jhi_userId);

        // Get all the biddersList where jhi_user equals to jhi_userId + 1
        defaultBiddersShouldNotBeFound("jhi_userId.equals=" + (jhi_userId + 1));
    }


    @Test
    @Transactional
    public void getAllBiddersBySolicitationsIsEqualToSomething() throws Exception {
        // Initialize the database
        Solicitations solicitations = SolicitationsResourceIntTest.createEntity(em);
        em.persist(solicitations);
        em.flush();
        bidders.setSolicitations(solicitations);
        biddersRepository.saveAndFlush(bidders);
        Long solicitationsId = solicitations.getId();

        // Get all the biddersList where solicitations equals to solicitationsId
        defaultBiddersShouldBeFound("solicitationsId.equals=" + solicitationsId);

        // Get all the biddersList where solicitations equals to solicitationsId + 1
        defaultBiddersShouldNotBeFound("solicitationsId.equals=" + (solicitationsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBiddersShouldBeFound(String filter) throws Exception {
        restBiddersMockMvc.perform(get("/api/bidders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bidders.getId().intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].bidCategories").value(hasItem(DEFAULT_BID_CATEGORIES.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBiddersShouldNotBeFound(String filter) throws Exception {
        restBiddersMockMvc.perform(get("/api/bidders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
            .solicitationId(UPDATED_SOLICITATION_ID)
            .name(UPDATED_NAME)
            .bidCategories(UPDATED_BID_CATEGORIES)
            .userId(UPDATED_USER_ID);
        BiddersDTO biddersDTO = biddersMapper.toDto(updatedBidders);

        restBiddersMockMvc.perform(put("/api/bidders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biddersDTO)))
            .andExpect(status().isOk());

        // Validate the Bidders in the database
        List<Bidders> biddersList = biddersRepository.findAll();
        assertThat(biddersList).hasSize(databaseSizeBeforeUpdate);
        Bidders testBidders = biddersList.get(biddersList.size() - 1);
        assertThat(testBidders.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
        assertThat(testBidders.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBidders.getBidCategories()).isEqualTo(UPDATED_BID_CATEGORIES);
        assertThat(testBidders.getUserId()).isEqualTo(UPDATED_USER_ID);
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
