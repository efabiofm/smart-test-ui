package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.TipoAlerta;
import com.quenti.smarttestui.repository.TipoAlertaRepository;
import com.quenti.smarttestui.service.TipoAlertaService;
import com.quenti.smarttestui.service.dto.TipoAlertaDTO;
import com.quenti.smarttestui.service.mapper.TipoAlertaMapper;

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
 * Test class for the TipoAlertaResource REST controller.
 *
 * @see TipoAlertaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class TipoAlertaResourceIntTest {

    private static final String DEFAULT_METODO = "AAAAAAAAAA";
    private static final String UPDATED_METODO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private TipoAlertaRepository tipoAlertaRepository;

    @Inject
    private TipoAlertaMapper tipoAlertaMapper;

    @Inject
    private TipoAlertaService tipoAlertaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTipoAlertaMockMvc;

    private TipoAlerta tipoAlerta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoAlertaResource tipoAlertaResource = new TipoAlertaResource();
        ReflectionTestUtils.setField(tipoAlertaResource, "tipoAlertaService", tipoAlertaService);
        this.restTipoAlertaMockMvc = MockMvcBuilders.standaloneSetup(tipoAlertaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAlerta createEntity(EntityManager em) {
        TipoAlerta tipoAlerta = new TipoAlerta()
                .metodo(DEFAULT_METODO)
                .activo(DEFAULT_ACTIVO);
        return tipoAlerta;
    }

    @Before
    public void initTest() {
        tipoAlerta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoAlerta() throws Exception {
        int databaseSizeBeforeCreate = tipoAlertaRepository.findAll().size();

        // Create the TipoAlerta
        TipoAlertaDTO tipoAlertaDTO = tipoAlertaMapper.tipoAlertaToTipoAlertaDTO(tipoAlerta);

        restTipoAlertaMockMvc.perform(post("/api/tipo-alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAlertaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoAlerta in the database
        List<TipoAlerta> tipoAlertaList = tipoAlertaRepository.findAll();
        assertThat(tipoAlertaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAlerta testTipoAlerta = tipoAlertaList.get(tipoAlertaList.size() - 1);
        assertThat(testTipoAlerta.getMetodo()).isEqualTo(DEFAULT_METODO);
        assertThat(testTipoAlerta.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createTipoAlertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoAlertaRepository.findAll().size();

        // Create the TipoAlerta with an existing ID
        TipoAlerta existingTipoAlerta = new TipoAlerta();
        existingTipoAlerta.setId(1L);
        TipoAlertaDTO existingTipoAlertaDTO = tipoAlertaMapper.tipoAlertaToTipoAlertaDTO(existingTipoAlerta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAlertaMockMvc.perform(post("/api/tipo-alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTipoAlertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TipoAlerta> tipoAlertaList = tipoAlertaRepository.findAll();
        assertThat(tipoAlertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMetodoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAlertaRepository.findAll().size();
        // set the field null
        tipoAlerta.setMetodo(null);

        // Create the TipoAlerta, which fails.
        TipoAlertaDTO tipoAlertaDTO = tipoAlertaMapper.tipoAlertaToTipoAlertaDTO(tipoAlerta);

        restTipoAlertaMockMvc.perform(post("/api/tipo-alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAlertaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoAlerta> tipoAlertaList = tipoAlertaRepository.findAll();
        assertThat(tipoAlertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoAlertas() throws Exception {
        // Initialize the database
        tipoAlertaRepository.saveAndFlush(tipoAlerta);

        // Get all the tipoAlertaList
        restTipoAlertaMockMvc.perform(get("/api/tipo-alertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAlerta.getId().intValue())))
            .andExpect(jsonPath("$.[*].metodo").value(hasItem(DEFAULT_METODO.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTipoAlerta() throws Exception {
        // Initialize the database
        tipoAlertaRepository.saveAndFlush(tipoAlerta);

        // Get the tipoAlerta
        restTipoAlertaMockMvc.perform(get("/api/tipo-alertas/{id}", tipoAlerta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoAlerta.getId().intValue()))
            .andExpect(jsonPath("$.metodo").value(DEFAULT_METODO.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoAlerta() throws Exception {
        // Get the tipoAlerta
        restTipoAlertaMockMvc.perform(get("/api/tipo-alertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoAlerta() throws Exception {
        // Initialize the database
        tipoAlertaRepository.saveAndFlush(tipoAlerta);
        int databaseSizeBeforeUpdate = tipoAlertaRepository.findAll().size();

        // Update the tipoAlerta
        TipoAlerta updatedTipoAlerta = tipoAlertaRepository.findOne(tipoAlerta.getId());
        updatedTipoAlerta
                .metodo(UPDATED_METODO)
                .activo(UPDATED_ACTIVO);
        TipoAlertaDTO tipoAlertaDTO = tipoAlertaMapper.tipoAlertaToTipoAlertaDTO(updatedTipoAlerta);

        restTipoAlertaMockMvc.perform(put("/api/tipo-alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAlertaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoAlerta in the database
        List<TipoAlerta> tipoAlertaList = tipoAlertaRepository.findAll();
        assertThat(tipoAlertaList).hasSize(databaseSizeBeforeUpdate);
        TipoAlerta testTipoAlerta = tipoAlertaList.get(tipoAlertaList.size() - 1);
        assertThat(testTipoAlerta.getMetodo()).isEqualTo(UPDATED_METODO);
        assertThat(testTipoAlerta.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoAlerta() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlertaRepository.findAll().size();

        // Create the TipoAlerta
        TipoAlertaDTO tipoAlertaDTO = tipoAlertaMapper.tipoAlertaToTipoAlertaDTO(tipoAlerta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoAlertaMockMvc.perform(put("/api/tipo-alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAlertaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoAlerta in the database
        List<TipoAlerta> tipoAlertaList = tipoAlertaRepository.findAll();
        assertThat(tipoAlertaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoAlerta() throws Exception {
        // Initialize the database
        tipoAlertaRepository.saveAndFlush(tipoAlerta);
        int databaseSizeBeforeDelete = tipoAlertaRepository.findAll().size();

        // Get the tipoAlerta
        restTipoAlertaMockMvc.perform(delete("/api/tipo-alertas/{id}", tipoAlerta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoAlerta> tipoAlertaList = tipoAlertaRepository.findAll();
        assertThat(tipoAlertaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
