package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Alerta;
import com.quenti.smarttestui.repository.AlertaRepository;
import com.quenti.smarttestui.service.AlertaService;
import com.quenti.smarttestui.service.dto.AlertaDTO;
import com.quenti.smarttestui.service.mapper.AlertaMapper;

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
 * Test class for the AlertaResource REST controller.
 *
 * @see AlertaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class AlertaResourceIntTest {

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private AlertaRepository alertaRepository;

    @Inject
    private AlertaMapper alertaMapper;

    @Inject
    private AlertaService alertaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlertaMockMvc;

    private Alerta alerta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlertaResource alertaResource = new AlertaResource();
        ReflectionTestUtils.setField(alertaResource, "alertaService", alertaService);
        this.restAlertaMockMvc = MockMvcBuilders.standaloneSetup(alertaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alerta createEntity(EntityManager em) {
        Alerta alerta = new Alerta()
                .mensaje(DEFAULT_MENSAJE)
                .activo(DEFAULT_ACTIVO);
        return alerta;
    }

    @Before
    public void initTest() {
        alerta = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlerta() throws Exception {
        int databaseSizeBeforeCreate = alertaRepository.findAll().size();

        // Create the Alerta
        AlertaDTO alertaDTO = alertaMapper.alertaToAlertaDTO(alerta);

        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isCreated());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeCreate + 1);
        Alerta testAlerta = alertaList.get(alertaList.size() - 1);
        assertThat(testAlerta.getMensaje()).isEqualTo(DEFAULT_MENSAJE);
        assertThat(testAlerta.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createAlertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaRepository.findAll().size();

        // Create the Alerta with an existing ID
        Alerta existingAlerta = new Alerta();
        existingAlerta.setId(1L);
        AlertaDTO existingAlertaDTO = alertaMapper.alertaToAlertaDTO(existingAlerta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAlertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMensajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaRepository.findAll().size();
        // set the field null
        alerta.setMensaje(null);

        // Create the Alerta, which fails.
        AlertaDTO alertaDTO = alertaMapper.alertaToAlertaDTO(alerta);

        restAlertaMockMvc.perform(post("/api/alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isBadRequest());

        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertas() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get all the alertaList
        restAlertaMockMvc.perform(get("/api/alertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alerta.getId().intValue())))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);

        // Get the alerta
        restAlertaMockMvc.perform(get("/api/alertas/{id}", alerta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alerta.getId().intValue()))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlerta() throws Exception {
        // Get the alerta
        restAlertaMockMvc.perform(get("/api/alertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        int databaseSizeBeforeUpdate = alertaRepository.findAll().size();

        // Update the alerta
        Alerta updatedAlerta = alertaRepository.findOne(alerta.getId());
        updatedAlerta
                .mensaje(UPDATED_MENSAJE)
                .activo(UPDATED_ACTIVO);
        AlertaDTO alertaDTO = alertaMapper.alertaToAlertaDTO(updatedAlerta);

        restAlertaMockMvc.perform(put("/api/alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isOk());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeUpdate);
        Alerta testAlerta = alertaList.get(alertaList.size() - 1);
        assertThat(testAlerta.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testAlerta.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingAlerta() throws Exception {
        int databaseSizeBeforeUpdate = alertaRepository.findAll().size();

        // Create the Alerta
        AlertaDTO alertaDTO = alertaMapper.alertaToAlertaDTO(alerta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlertaMockMvc.perform(put("/api/alertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDTO)))
            .andExpect(status().isCreated());

        // Validate the Alerta in the database
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlerta() throws Exception {
        // Initialize the database
        alertaRepository.saveAndFlush(alerta);
        int databaseSizeBeforeDelete = alertaRepository.findAll().size();

        // Get the alerta
        restAlertaMockMvc.perform(delete("/api/alertas/{id}", alerta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alerta> alertaList = alertaRepository.findAll();
        assertThat(alertaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
