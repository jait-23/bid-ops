package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.SolicitationAuthor;
import com.predix.bidopscore.repository.SolicitationAuthorRepository;
import com.predix.bidopscore.service.SolicitationAuthorService;
import com.predix.bidopscore.service.dto.SolicitationAuthorDTO;
import com.predix.bidopscore.service.mapper.SolicitationAuthorMapper;
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
 * Test class for the SolicitationAuthorResource REST controller.
 *
 * @see SolicitationAuthorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class SolicitationAuthorResourceIntTest {

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final Long UPDATED_AUTHOR_ID = 2L;

    private static final Long DEFAULT_SOLICITATION_ID = 1L;
    private static final Long UPDATED_SOLICITATION_ID = 2L;

    @Autowired
    private SolicitationAuthorRepository solicitationAuthorRepository;

    @Autowired
    private SolicitationAuthorMapper solicitationAuthorMapper;

    @Autowired
    private SolicitationAuthorService solicitationAuthorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolicitationAuthorMockMvc;

    private SolicitationAuthor solicitationAuthor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolicitationAuthorResource solicitationAuthorResource = new SolicitationAuthorResource(solicitationAuthorService);
        this.restSolicitationAuthorMockMvc = MockMvcBuilders.standaloneSetup(solicitationAuthorResource)
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
    public static SolicitationAuthor createEntity(EntityManager em) {
        SolicitationAuthor solicitationAuthor = new SolicitationAuthor()
            .authorId(DEFAULT_AUTHOR_ID)
            .solicitationId(DEFAULT_SOLICITATION_ID);
        return solicitationAuthor;
    }

    @Before
    public void initTest() {
        solicitationAuthor = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitationAuthor() throws Exception {
        int databaseSizeBeforeCreate = solicitationAuthorRepository.findAll().size();

        // Create the SolicitationAuthor
        SolicitationAuthorDTO solicitationAuthorDTO = solicitationAuthorMapper.toDto(solicitationAuthor);
        restSolicitationAuthorMockMvc.perform(post("/api/solicitation-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationAuthorDTO)))
            .andExpect(status().isCreated());

        // Validate the SolicitationAuthor in the database
        List<SolicitationAuthor> solicitationAuthorList = solicitationAuthorRepository.findAll();
        assertThat(solicitationAuthorList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitationAuthor testSolicitationAuthor = solicitationAuthorList.get(solicitationAuthorList.size() - 1);
        assertThat(testSolicitationAuthor.getAuthorId()).isEqualTo(DEFAULT_AUTHOR_ID);
        assertThat(testSolicitationAuthor.getSolicitationId()).isEqualTo(DEFAULT_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void createSolicitationAuthorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitationAuthorRepository.findAll().size();

        // Create the SolicitationAuthor with an existing ID
        solicitationAuthor.setId(1L);
        SolicitationAuthorDTO solicitationAuthorDTO = solicitationAuthorMapper.toDto(solicitationAuthor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitationAuthorMockMvc.perform(post("/api/solicitation-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationAuthorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitationAuthor in the database
        List<SolicitationAuthor> solicitationAuthorList = solicitationAuthorRepository.findAll();
        assertThat(solicitationAuthorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSolicitationAuthors() throws Exception {
        // Initialize the database
        solicitationAuthorRepository.saveAndFlush(solicitationAuthor);

        // Get all the solicitationAuthorList
        restSolicitationAuthorMockMvc.perform(get("/api/solicitation-authors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitationAuthor.getId().intValue())))
            .andExpect(jsonPath("$.[*].authorId").value(hasItem(DEFAULT_AUTHOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].solicitationId").value(hasItem(DEFAULT_SOLICITATION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSolicitationAuthor() throws Exception {
        // Initialize the database
        solicitationAuthorRepository.saveAndFlush(solicitationAuthor);

        // Get the solicitationAuthor
        restSolicitationAuthorMockMvc.perform(get("/api/solicitation-authors/{id}", solicitationAuthor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solicitationAuthor.getId().intValue()))
            .andExpect(jsonPath("$.authorId").value(DEFAULT_AUTHOR_ID.intValue()))
            .andExpect(jsonPath("$.solicitationId").value(DEFAULT_SOLICITATION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSolicitationAuthor() throws Exception {
        // Get the solicitationAuthor
        restSolicitationAuthorMockMvc.perform(get("/api/solicitation-authors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitationAuthor() throws Exception {
        // Initialize the database
        solicitationAuthorRepository.saveAndFlush(solicitationAuthor);
        int databaseSizeBeforeUpdate = solicitationAuthorRepository.findAll().size();

        // Update the solicitationAuthor
        SolicitationAuthor updatedSolicitationAuthor = solicitationAuthorRepository.findOne(solicitationAuthor.getId());
        // Disconnect from session so that the updates on updatedSolicitationAuthor are not directly saved in db
        em.detach(updatedSolicitationAuthor);
        updatedSolicitationAuthor
            .authorId(UPDATED_AUTHOR_ID)
            .solicitationId(UPDATED_SOLICITATION_ID);
        SolicitationAuthorDTO solicitationAuthorDTO = solicitationAuthorMapper.toDto(updatedSolicitationAuthor);

        restSolicitationAuthorMockMvc.perform(put("/api/solicitation-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationAuthorDTO)))
            .andExpect(status().isOk());

        // Validate the SolicitationAuthor in the database
        List<SolicitationAuthor> solicitationAuthorList = solicitationAuthorRepository.findAll();
        assertThat(solicitationAuthorList).hasSize(databaseSizeBeforeUpdate);
        SolicitationAuthor testSolicitationAuthor = solicitationAuthorList.get(solicitationAuthorList.size() - 1);
        assertThat(testSolicitationAuthor.getAuthorId()).isEqualTo(UPDATED_AUTHOR_ID);
        assertThat(testSolicitationAuthor.getSolicitationId()).isEqualTo(UPDATED_SOLICITATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitationAuthor() throws Exception {
        int databaseSizeBeforeUpdate = solicitationAuthorRepository.findAll().size();

        // Create the SolicitationAuthor
        SolicitationAuthorDTO solicitationAuthorDTO = solicitationAuthorMapper.toDto(solicitationAuthor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolicitationAuthorMockMvc.perform(put("/api/solicitation-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitationAuthorDTO)))
            .andExpect(status().isCreated());

        // Validate the SolicitationAuthor in the database
        List<SolicitationAuthor> solicitationAuthorList = solicitationAuthorRepository.findAll();
        assertThat(solicitationAuthorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolicitationAuthor() throws Exception {
        // Initialize the database
        solicitationAuthorRepository.saveAndFlush(solicitationAuthor);
        int databaseSizeBeforeDelete = solicitationAuthorRepository.findAll().size();

        // Get the solicitationAuthor
        restSolicitationAuthorMockMvc.perform(delete("/api/solicitation-authors/{id}", solicitationAuthor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SolicitationAuthor> solicitationAuthorList = solicitationAuthorRepository.findAll();
        assertThat(solicitationAuthorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitationAuthor.class);
        SolicitationAuthor solicitationAuthor1 = new SolicitationAuthor();
        solicitationAuthor1.setId(1L);
        SolicitationAuthor solicitationAuthor2 = new SolicitationAuthor();
        solicitationAuthor2.setId(solicitationAuthor1.getId());
        assertThat(solicitationAuthor1).isEqualTo(solicitationAuthor2);
        solicitationAuthor2.setId(2L);
        assertThat(solicitationAuthor1).isNotEqualTo(solicitationAuthor2);
        solicitationAuthor1.setId(null);
        assertThat(solicitationAuthor1).isNotEqualTo(solicitationAuthor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitationAuthorDTO.class);
        SolicitationAuthorDTO solicitationAuthorDTO1 = new SolicitationAuthorDTO();
        solicitationAuthorDTO1.setId(1L);
        SolicitationAuthorDTO solicitationAuthorDTO2 = new SolicitationAuthorDTO();
        assertThat(solicitationAuthorDTO1).isNotEqualTo(solicitationAuthorDTO2);
        solicitationAuthorDTO2.setId(solicitationAuthorDTO1.getId());
        assertThat(solicitationAuthorDTO1).isEqualTo(solicitationAuthorDTO2);
        solicitationAuthorDTO2.setId(2L);
        assertThat(solicitationAuthorDTO1).isNotEqualTo(solicitationAuthorDTO2);
        solicitationAuthorDTO1.setId(null);
        assertThat(solicitationAuthorDTO1).isNotEqualTo(solicitationAuthorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(solicitationAuthorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(solicitationAuthorMapper.fromId(null)).isNull();
    }
}
