package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Header;
import com.quenti.smarttestui.repository.HeaderRepository;
import com.quenti.smarttestui.service.HeaderService;
import com.quenti.smarttestui.service.dto.HeaderDTO;
import com.quenti.smarttestui.service.mapper.HeaderMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HeaderResource REST controller.
 *
 * @see HeaderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class HeaderResourceIntTest {

    private static final Integer DEFAULT_SERVICE_GROUP_ID = 1;
    private static final Integer UPDATED_SERVICE_GROUP_ID = 2;

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = true;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private HeaderRepository headerRepository;

    @Inject
    private HeaderMapper headerMapper;

    @Inject
    private HeaderService headerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHeaderMockMvc;

    private Header header;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeaderResource headerResource = new HeaderResource();
        ReflectionTestUtils.setField(headerResource, "headerService", headerService);
        this.restHeaderMockMvc = MockMvcBuilders.standaloneSetup(headerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Header createEntity(EntityManager em) {
        Header header = new Header()
                .serviceGroupId(DEFAULT_SERVICE_GROUP_ID)
                .token(DEFAULT_TOKEN)
                .activo(DEFAULT_ACTIVO);
        return header;
    }

    @Before
    public void initTest() {
        header = createEntity(em);
    }

    @Test
    @Transactional
    public void createHeader() throws Exception {
        int databaseSizeBeforeCreate = headerRepository.findAll().size();

        // Create the Header
        HeaderDTO headerDTO = headerMapper.headerToHeaderDTO(header);

        restHeaderMockMvc.perform(post("/api/headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(headerDTO)))
            .andExpect(status().isCreated());

        // Validate the Header in the database
        List<Header> headerList = headerRepository.findAll();
        assertThat(headerList).hasSize(databaseSizeBeforeCreate + 1);
        Header testHeader = headerList.get(headerList.size() - 1);
        assertThat(testHeader.getServiceGroupId()).isEqualTo(DEFAULT_SERVICE_GROUP_ID);
        assertThat(testHeader.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testHeader.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = headerRepository.findAll().size();

        // Create the Header with an existing ID
        Header existingHeader = new Header();
        existingHeader.setId(1L);
        HeaderDTO existingHeaderDTO = headerMapper.headerToHeaderDTO(existingHeader);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeaderMockMvc.perform(post("/api/headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHeaderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Header> headerList = headerRepository.findAll();
        assertThat(headerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = headerRepository.findAll().size();
        // set the field null
        header.setToken(null);

        // Create the Header, which fails.
        HeaderDTO headerDTO = headerMapper.headerToHeaderDTO(header);

        restHeaderMockMvc.perform(post("/api/headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(headerDTO)))
            .andExpect(status().isBadRequest());

        List<Header> headerList = headerRepository.findAll();
        assertThat(headerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeaders() throws Exception {
        // Initialize the database
        headerRepository.saveAndFlush(header);

        // Get all the headerList
        restHeaderMockMvc.perform(get("/api/headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(header.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceGroupId").value(hasItem(DEFAULT_SERVICE_GROUP_ID)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getHeader() throws Exception {
        // Initialize the database
        headerRepository.saveAndFlush(header);

        // Get the header
        restHeaderMockMvc.perform(get("/api/headers/{id}", header.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(header.getId().intValue()))
            .andExpect(jsonPath("$.serviceGroupId").value(DEFAULT_SERVICE_GROUP_ID))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHeader() throws Exception {
        // Get the header
        restHeaderMockMvc.perform(get("/api/headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeader() throws Exception {
        // Initialize the database
        headerRepository.saveAndFlush(header);
        int databaseSizeBeforeUpdate = headerRepository.findAll().size();

        // Update the header
        Header updatedHeader = headerRepository.findOne(header.getId());
        updatedHeader
                .serviceGroupId(UPDATED_SERVICE_GROUP_ID)
                .token(UPDATED_TOKEN)
                .activo(UPDATED_ACTIVO);
        HeaderDTO headerDTO = headerMapper.headerToHeaderDTO(updatedHeader);

        restHeaderMockMvc.perform(put("/api/headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(headerDTO)))
            .andExpect(status().isOk());

        // Validate the Header in the database
        List<Header> headerList = headerRepository.findAll();
        assertThat(headerList).hasSize(databaseSizeBeforeUpdate);
        Header testHeader = headerList.get(headerList.size() - 1);
        assertThat(testHeader.getServiceGroupId()).isEqualTo(UPDATED_SERVICE_GROUP_ID);
        assertThat(testHeader.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testHeader.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingHeader() throws Exception {
        int databaseSizeBeforeUpdate = headerRepository.findAll().size();

        // Create the Header
        HeaderDTO headerDTO = headerMapper.headerToHeaderDTO(header);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHeaderMockMvc.perform(put("/api/headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(headerDTO)))
            .andExpect(status().isCreated());

        // Validate the Header in the database
        List<Header> headerList = headerRepository.findAll();
        assertThat(headerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHeader() throws Exception {
        // Initialize the database
        headerRepository.saveAndFlush(header);
        int databaseSizeBeforeDelete = headerRepository.findAll().size();

        // Get the header
        restHeaderMockMvc.perform(delete("/api/headers/{id}", header.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Header> headerList = headerRepository.findByActivoTrue();
        assertThat(headerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
