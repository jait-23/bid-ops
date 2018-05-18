package com.predix.bidopscore.web.rest;

import com.predix.bidopscore.BidopscoreApp;

import com.predix.bidopscore.domain.Files;
import com.predix.bidopscore.domain.Solicitations;
import com.predix.bidopscore.repository.FilesRepository;
import com.predix.bidopscore.service.FilesService;
import com.predix.bidopscore.service.dto.FilesDTO;
import com.predix.bidopscore.service.mapper.FilesMapper;
import com.predix.bidopscore.web.rest.errors.ExceptionTranslator;
import com.predix.bidopscore.service.dto.FilesCriteria;
import com.predix.bidopscore.service.FilesQueryService;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.predix.bidopscore.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FilesResource REST controller.
 *
 * @see FilesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BidopscoreApp.class)
public class FilesResourceIntTest {

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOLICITATIONS = 1;
    private static final Integer UPDATED_SOLICITATIONS = 2;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private FilesService filesService;

    @Autowired
    private FilesQueryService filesQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilesMockMvc;

    private Files files;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilesResource filesResource = new FilesResource(filesService, filesQueryService);
        this.restFilesMockMvc = MockMvcBuilders.standaloneSetup(filesResource)
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
    public static Files createEntity(EntityManager em) {
        Files files = new Files()
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .type(DEFAULT_TYPE)
            .documentName(DEFAULT_DOCUMENT_NAME)
            .solicitations(DEFAULT_SOLICITATIONS);
        return files;
    }

    @Before
    public void initTest() {
        files = createEntity(em);
    }

    @Test
    @Transactional
    public void createFiles() throws Exception {
        int databaseSizeBeforeCreate = filesRepository.findAll().size();

        // Create the Files
        FilesDTO filesDTO = filesMapper.toDto(files);
        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isCreated());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate + 1);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testFiles.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testFiles.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFiles.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testFiles.getSolicitations()).isEqualTo(DEFAULT_SOLICITATIONS);
    }

    @Test
    @Transactional
    public void createFilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filesRepository.findAll().size();

        // Create the Files with an existing ID
        files.setId(1L);
        FilesDTO filesDTO = filesMapper.toDto(files);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList
        restFilesMockMvc.perform(get("/api/files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(files.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].solicitations").value(hasItem(DEFAULT_SOLICITATIONS)));
    }

    @Test
    @Transactional
    public void getFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get the files
        restFilesMockMvc.perform(get("/api/files/{id}", files.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(files.getId().intValue()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME.toString()))
            .andExpect(jsonPath("$.solicitations").value(DEFAULT_SOLICITATIONS));
    }

    @Test
    @Transactional
    public void getAllFilesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where type equals to DEFAULT_TYPE
        defaultFilesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the filesList where type equals to UPDATED_TYPE
        defaultFilesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllFilesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultFilesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the filesList where type equals to UPDATED_TYPE
        defaultFilesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllFilesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where type is not null
        defaultFilesShouldBeFound("type.specified=true");

        // Get all the filesList where type is null
        defaultFilesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilesByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultFilesShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the filesList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultFilesShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllFilesByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultFilesShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the filesList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultFilesShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllFilesByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where documentName is not null
        defaultFilesShouldBeFound("documentName.specified=true");

        // Get all the filesList where documentName is null
        defaultFilesShouldNotBeFound("documentName.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilesBySolicitationsIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where solicitations equals to DEFAULT_SOLICITATIONS
        defaultFilesShouldBeFound("solicitations.equals=" + DEFAULT_SOLICITATIONS);

        // Get all the filesList where solicitations equals to UPDATED_SOLICITATIONS
        defaultFilesShouldNotBeFound("solicitations.equals=" + UPDATED_SOLICITATIONS);
    }

    @Test
    @Transactional
    public void getAllFilesBySolicitationsIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where solicitations in DEFAULT_SOLICITATIONS or UPDATED_SOLICITATIONS
        defaultFilesShouldBeFound("solicitations.in=" + DEFAULT_SOLICITATIONS + "," + UPDATED_SOLICITATIONS);

        // Get all the filesList where solicitations equals to UPDATED_SOLICITATIONS
        defaultFilesShouldNotBeFound("solicitations.in=" + UPDATED_SOLICITATIONS);
    }

    @Test
    @Transactional
    public void getAllFilesBySolicitationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where solicitations is not null
        defaultFilesShouldBeFound("solicitations.specified=true");

        // Get all the filesList where solicitations is null
        defaultFilesShouldNotBeFound("solicitations.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilesBySolicitationsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where solicitations greater than or equals to DEFAULT_SOLICITATIONS
        defaultFilesShouldBeFound("solicitations.greaterOrEqualThan=" + DEFAULT_SOLICITATIONS);

        // Get all the filesList where solicitations greater than or equals to UPDATED_SOLICITATIONS
        defaultFilesShouldNotBeFound("solicitations.greaterOrEqualThan=" + UPDATED_SOLICITATIONS);
    }

    @Test
    @Transactional
    public void getAllFilesBySolicitationsIsLessThanSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where solicitations less than or equals to DEFAULT_SOLICITATIONS
        defaultFilesShouldNotBeFound("solicitations.lessThan=" + DEFAULT_SOLICITATIONS);

        // Get all the filesList where solicitations less than or equals to UPDATED_SOLICITATIONS
        defaultFilesShouldBeFound("solicitations.lessThan=" + UPDATED_SOLICITATIONS);
    }


    @Test
    @Transactional
    public void getAllFilesBySolicitationsFilesIsEqualToSomething() throws Exception {
        // Initialize the database
        Solicitations solicitationsFiles = SolicitationsResourceIntTest.createEntity(em);
        em.persist(solicitationsFiles);
        em.flush();
        files.setSolicitationsFiles(solicitationsFiles);
        filesRepository.saveAndFlush(files);
        Long solicitationsFilesId = solicitationsFiles.getId();

        // Get all the filesList where solicitationsFiles equals to solicitationsFilesId
        defaultFilesShouldBeFound("solicitationsFilesId.equals=" + solicitationsFilesId);

        // Get all the filesList where solicitationsFiles equals to solicitationsFilesId + 1
        defaultFilesShouldNotBeFound("solicitationsFilesId.equals=" + (solicitationsFilesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFilesShouldBeFound(String filter) throws Exception {
        restFilesMockMvc.perform(get("/api/files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(files.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].solicitations").value(hasItem(DEFAULT_SOLICITATIONS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFilesShouldNotBeFound(String filter) throws Exception {
        restFilesMockMvc.perform(get("/api/files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingFiles() throws Exception {
        // Get the files
        restFilesMockMvc.perform(get("/api/files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files
        Files updatedFiles = filesRepository.findOne(files.getId());
        // Disconnect from session so that the updates on updatedFiles are not directly saved in db
        em.detach(updatedFiles);
        updatedFiles
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .documentName(UPDATED_DOCUMENT_NAME)
            .solicitations(UPDATED_SOLICITATIONS);
        FilesDTO filesDTO = filesMapper.toDto(updatedFiles);

        restFilesMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testFiles.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFiles.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testFiles.getSolicitations()).isEqualTo(UPDATED_SOLICITATIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Create the Files
        FilesDTO filesDTO = filesMapper.toDto(files);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilesMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isCreated());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);
        int databaseSizeBeforeDelete = filesRepository.findAll().size();

        // Get the files
        restFilesMockMvc.perform(delete("/api/files/{id}", files.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Files.class);
        Files files1 = new Files();
        files1.setId(1L);
        Files files2 = new Files();
        files2.setId(files1.getId());
        assertThat(files1).isEqualTo(files2);
        files2.setId(2L);
        assertThat(files1).isNotEqualTo(files2);
        files1.setId(null);
        assertThat(files1).isNotEqualTo(files2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilesDTO.class);
        FilesDTO filesDTO1 = new FilesDTO();
        filesDTO1.setId(1L);
        FilesDTO filesDTO2 = new FilesDTO();
        assertThat(filesDTO1).isNotEqualTo(filesDTO2);
        filesDTO2.setId(filesDTO1.getId());
        assertThat(filesDTO1).isEqualTo(filesDTO2);
        filesDTO2.setId(2L);
        assertThat(filesDTO1).isNotEqualTo(filesDTO2);
        filesDTO1.setId(null);
        assertThat(filesDTO1).isNotEqualTo(filesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(filesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(filesMapper.fromId(null)).isNull();
    }
}
