package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.TipoHeader;
import com.quenti.smarttestui.repository.TipoHeaderRepository;
import com.quenti.smarttestui.service.TipoHeaderService;
import com.quenti.smarttestui.service.dto.TipoHeaderDTO;
import com.quenti.smarttestui.service.mapper.TipoHeaderMapper;

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
 * Test class for the TipoHeaderResource REST controller.
 *
 * @see TipoHeaderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class TipoHeaderResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = true;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private TipoHeaderRepository tipoHeaderRepository;

    @Inject
    private TipoHeaderMapper tipoHeaderMapper;

    @Inject
    private TipoHeaderService tipoHeaderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTipoHeaderMockMvc;

    private TipoHeader tipoHeader;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoHeaderResource tipoHeaderResource = new TipoHeaderResource();
        ReflectionTestUtils.setField(tipoHeaderResource, "tipoHeaderService", tipoHeaderService);
        this.restTipoHeaderMockMvc = MockMvcBuilders.standaloneSetup(tipoHeaderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoHeader createEntity(EntityManager em) {
        TipoHeader tipoHeader = new TipoHeader()
                .nombre(DEFAULT_NOMBRE)
                .activo(DEFAULT_ACTIVO);
        return tipoHeader;
    }

    @Before
    public void initTest() {
        tipoHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoHeader() throws Exception {
        int databaseSizeBeforeCreate = tipoHeaderRepository.findAll().size();

        // Create the TipoHeader
        TipoHeaderDTO tipoHeaderDTO = tipoHeaderMapper.tipoHeaderToTipoHeaderDTO(tipoHeader);

        restTipoHeaderMockMvc.perform(post("/api/tipo-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoHeaderDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoHeader in the database
        List<TipoHeader> tipoHeaderList = tipoHeaderRepository.findAll();
        assertThat(tipoHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        TipoHeader testTipoHeader = tipoHeaderList.get(tipoHeaderList.size() - 1);
        assertThat(testTipoHeader.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoHeader.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createTipoHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoHeaderRepository.findAll().size();

        // Create the TipoHeader with an existing ID
        TipoHeader existingTipoHeader = new TipoHeader();
        existingTipoHeader.setId(1L);
        TipoHeaderDTO existingTipoHeaderDTO = tipoHeaderMapper.tipoHeaderToTipoHeaderDTO(existingTipoHeader);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoHeaderMockMvc.perform(post("/api/tipo-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTipoHeaderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TipoHeader> tipoHeaderList = tipoHeaderRepository.findAll();
        assertThat(tipoHeaderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoHeaderRepository.findAll().size();
        // set the field null
        tipoHeader.setNombre(null);

        // Create the TipoHeader, which fails.
        TipoHeaderDTO tipoHeaderDTO = tipoHeaderMapper.tipoHeaderToTipoHeaderDTO(tipoHeader);

        restTipoHeaderMockMvc.perform(post("/api/tipo-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoHeaderDTO)))
            .andExpect(status().isBadRequest());

        List<TipoHeader> tipoHeaderList = tipoHeaderRepository.findAll();
        assertThat(tipoHeaderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoHeaders() throws Exception {
        // Initialize the database
        tipoHeaderRepository.saveAndFlush(tipoHeader);

        // Get all the tipoHeaderList
        restTipoHeaderMockMvc.perform(get("/api/tipo-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTipoHeader() throws Exception {
        // Initialize the database
        tipoHeaderRepository.saveAndFlush(tipoHeader);

        // Get the tipoHeader
        restTipoHeaderMockMvc.perform(get("/api/tipo-headers/{id}", tipoHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoHeader.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoHeader() throws Exception {
        // Get the tipoHeader
        restTipoHeaderMockMvc.perform(get("/api/tipo-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoHeader() throws Exception {
        // Initialize the database
        tipoHeaderRepository.saveAndFlush(tipoHeader);
        int databaseSizeBeforeUpdate = tipoHeaderRepository.findAll().size();

        // Update the tipoHeader
        TipoHeader updatedTipoHeader = tipoHeaderRepository.findOne(tipoHeader.getId());
        updatedTipoHeader
                .nombre(UPDATED_NOMBRE)
                .activo(UPDATED_ACTIVO);
        TipoHeaderDTO tipoHeaderDTO = tipoHeaderMapper.tipoHeaderToTipoHeaderDTO(updatedTipoHeader);

        restTipoHeaderMockMvc.perform(put("/api/tipo-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoHeaderDTO)))
            .andExpect(status().isOk());

        // Validate the TipoHeader in the database
        List<TipoHeader> tipoHeaderList = tipoHeaderRepository.findAll();
        assertThat(tipoHeaderList).hasSize(databaseSizeBeforeUpdate);
        TipoHeader testTipoHeader = tipoHeaderList.get(tipoHeaderList.size() - 1);
        assertThat(testTipoHeader.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoHeader.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoHeader() throws Exception {
        int databaseSizeBeforeUpdate = tipoHeaderRepository.findAll().size();

        // Create the TipoHeader
        TipoHeaderDTO tipoHeaderDTO = tipoHeaderMapper.tipoHeaderToTipoHeaderDTO(tipoHeader);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoHeaderMockMvc.perform(put("/api/tipo-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoHeaderDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoHeader in the database
        List<TipoHeader> tipoHeaderList = tipoHeaderRepository.findAll();
        assertThat(tipoHeaderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoHeader() throws Exception {
        // Initialize the database
        tipoHeaderRepository.saveAndFlush(tipoHeader);
        int databaseSizeBeforeDelete = tipoHeaderRepository.findAll().size();

        // Get the tipoHeader
        restTipoHeaderMockMvc.perform(delete("/api/tipo-headers/{id}", tipoHeader.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoHeader> tipoHeaderList = tipoHeaderRepository.findByActivoTrue();
        assertThat(tipoHeaderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
