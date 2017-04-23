package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.EjecucionPrueba;
import com.quenti.smarttestui.repository.EjecucionPruebaRepository;
import com.quenti.smarttestui.service.EjecucionPruebaService;
import com.quenti.smarttestui.service.dto.EjecucionPruebaDTO;
import com.quenti.smarttestui.service.mapper.EjecucionPruebaMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EjecucionPruebaResource REST controller.
 *
 * @see EjecucionPruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class EjecucionPruebaResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_TIEMPO_RESPUESTA = new Long(1);
    private static final Long UPDATED_TIEMPO_RESPUESTA = new Long(2);

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_JH_USER_ID = 1;
    private static final Integer UPDATED_JH_USER_ID = 2;

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private EjecucionPruebaRepository ejecucionPruebaRepository;

    @Inject
    private EjecucionPruebaMapper ejecucionPruebaMapper;

    @Inject
    private EjecucionPruebaService ejecucionPruebaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEjecucionPruebaMockMvc;

    private EjecucionPrueba ejecucionPrueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EjecucionPruebaResource ejecucionPruebaResource = new EjecucionPruebaResource();
        ReflectionTestUtils.setField(ejecucionPruebaResource, "ejecucionPruebaService", ejecucionPruebaService);
        this.restEjecucionPruebaMockMvc = MockMvcBuilders.standaloneSetup(ejecucionPruebaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EjecucionPrueba createEntity(EntityManager em) {
        EjecucionPrueba ejecucionPrueba = new EjecucionPrueba()
                .fecha(DEFAULT_FECHA)
                .tiempoRespuesta(DEFAULT_TIEMPO_RESPUESTA)
                .resultado(DEFAULT_RESULTADO)
                .jhUserId(DEFAULT_JH_USER_ID)
                .body(DEFAULT_BODY)
                .activo(DEFAULT_ACTIVO);
        return ejecucionPrueba;
    }

    @Before
    public void initTest() {
        ejecucionPrueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createEjecucionPrueba() throws Exception {
        int databaseSizeBeforeCreate = ejecucionPruebaRepository.findAll().size();

        // Create the EjecucionPrueba
        EjecucionPruebaDTO ejecucionPruebaDTO = ejecucionPruebaMapper.ejecucionPruebaToEjecucionPruebaDTO(ejecucionPrueba);

        restEjecucionPruebaMockMvc.perform(post("/api/ejecucion-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejecucionPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the EjecucionPrueba in the database
        List<EjecucionPrueba> ejecucionPruebaList = ejecucionPruebaRepository.findAll();
        assertThat(ejecucionPruebaList).hasSize(databaseSizeBeforeCreate + 1);
        EjecucionPrueba testEjecucionPrueba = ejecucionPruebaList.get(ejecucionPruebaList.size() - 1);
        assertThat(testEjecucionPrueba.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEjecucionPrueba.getTiempoRespuesta()).isEqualTo(DEFAULT_TIEMPO_RESPUESTA);
        assertThat(testEjecucionPrueba.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testEjecucionPrueba.getJhUserId()).isEqualTo(DEFAULT_JH_USER_ID);
        assertThat(testEjecucionPrueba.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testEjecucionPrueba.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createEjecucionPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ejecucionPruebaRepository.findAll().size();

        // Create the EjecucionPrueba with an existing ID
        EjecucionPrueba existingEjecucionPrueba = new EjecucionPrueba();
        existingEjecucionPrueba.setId(1L);
        EjecucionPruebaDTO existingEjecucionPruebaDTO = ejecucionPruebaMapper.ejecucionPruebaToEjecucionPruebaDTO(existingEjecucionPrueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEjecucionPruebaMockMvc.perform(post("/api/ejecucion-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEjecucionPruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EjecucionPrueba> ejecucionPruebaList = ejecucionPruebaRepository.findAll();
        assertThat(ejecucionPruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEjecucionPruebas() throws Exception {
        // Initialize the database
        ejecucionPruebaRepository.saveAndFlush(ejecucionPrueba);

        // Get all the ejecucionPruebaList
        restEjecucionPruebaMockMvc.perform(get("/api/ejecucion-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ejecucionPrueba.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].tiempoRespuesta").value(hasItem(DEFAULT_TIEMPO_RESPUESTA)))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].jhUserId").value(hasItem(DEFAULT_JH_USER_ID)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getEjecucionPrueba() throws Exception {
        // Initialize the database
        ejecucionPruebaRepository.saveAndFlush(ejecucionPrueba);

        // Get the ejecucionPrueba
        restEjecucionPruebaMockMvc.perform(get("/api/ejecucion-pruebas/{id}", ejecucionPrueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ejecucionPrueba.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.tiempoRespuesta").value(DEFAULT_TIEMPO_RESPUESTA))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.toString()))
            .andExpect(jsonPath("$.jhUserId").value(DEFAULT_JH_USER_ID))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEjecucionPrueba() throws Exception {
        // Get the ejecucionPrueba
        restEjecucionPruebaMockMvc.perform(get("/api/ejecucion-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEjecucionPrueba() throws Exception {
        // Initialize the database
        ejecucionPruebaRepository.saveAndFlush(ejecucionPrueba);
        int databaseSizeBeforeUpdate = ejecucionPruebaRepository.findAll().size();

        // Update the ejecucionPrueba
        EjecucionPrueba updatedEjecucionPrueba = ejecucionPruebaRepository.findOne(ejecucionPrueba.getId());
        updatedEjecucionPrueba
                .fecha(UPDATED_FECHA)
                .tiempoRespuesta(UPDATED_TIEMPO_RESPUESTA)
                .resultado(UPDATED_RESULTADO)
                .jhUserId(UPDATED_JH_USER_ID)
                .body(UPDATED_BODY)
                .activo(UPDATED_ACTIVO);
        EjecucionPruebaDTO ejecucionPruebaDTO = ejecucionPruebaMapper.ejecucionPruebaToEjecucionPruebaDTO(updatedEjecucionPrueba);

        restEjecucionPruebaMockMvc.perform(put("/api/ejecucion-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejecucionPruebaDTO)))
            .andExpect(status().isOk());

        // Validate the EjecucionPrueba in the database
        List<EjecucionPrueba> ejecucionPruebaList = ejecucionPruebaRepository.findAll();
        assertThat(ejecucionPruebaList).hasSize(databaseSizeBeforeUpdate);
        EjecucionPrueba testEjecucionPrueba = ejecucionPruebaList.get(ejecucionPruebaList.size() - 1);
        assertThat(testEjecucionPrueba.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEjecucionPrueba.getTiempoRespuesta()).isEqualTo(UPDATED_TIEMPO_RESPUESTA);
        assertThat(testEjecucionPrueba.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testEjecucionPrueba.getJhUserId()).isEqualTo(UPDATED_JH_USER_ID);
        assertThat(testEjecucionPrueba.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testEjecucionPrueba.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingEjecucionPrueba() throws Exception {
        int databaseSizeBeforeUpdate = ejecucionPruebaRepository.findAll().size();

        // Create the EjecucionPrueba
        EjecucionPruebaDTO ejecucionPruebaDTO = ejecucionPruebaMapper.ejecucionPruebaToEjecucionPruebaDTO(ejecucionPrueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEjecucionPruebaMockMvc.perform(put("/api/ejecucion-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejecucionPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the EjecucionPrueba in the database
        List<EjecucionPrueba> ejecucionPruebaList = ejecucionPruebaRepository.findAll();
        assertThat(ejecucionPruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEjecucionPrueba() throws Exception {
        // Initialize the database
        ejecucionPruebaRepository.saveAndFlush(ejecucionPrueba);
        int databaseSizeBeforeDelete = ejecucionPruebaRepository.findAll().size();

        // Get the ejecucionPrueba
        restEjecucionPruebaMockMvc.perform(delete("/api/ejecucion-pruebas/{id}", ejecucionPrueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EjecucionPrueba> ejecucionPruebaList = ejecucionPruebaRepository.findAll();
        assertThat(ejecucionPruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
