package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Prueba;
import com.quenti.smarttestui.repository.PruebaRepository;
import com.quenti.smarttestui.service.PruebaService;
import com.quenti.smarttestui.service.dto.PruebaDTO;
import com.quenti.smarttestui.service.mapper.PruebaMapper;

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
 * Test class for the PruebaResource REST controller.
 *
 * @see PruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class PruebaResourceIntTest {

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = true;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private PruebaRepository pruebaRepository;

    @Inject
    private PruebaMapper pruebaMapper;

    @Inject
    private PruebaService pruebaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPruebaMockMvc;

    private Prueba prueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PruebaResource pruebaResource = new PruebaResource();
        ReflectionTestUtils.setField(pruebaResource, "pruebaService", pruebaService);
        this.restPruebaMockMvc = MockMvcBuilders.standaloneSetup(pruebaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prueba createEntity(EntityManager em) {
        Prueba prueba = new Prueba()
                .body(DEFAULT_BODY)
                .activo(DEFAULT_ACTIVO);
        return prueba;
    }

    @Before
    public void initTest() {
        prueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrueba() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();

        // Create the Prueba
        PruebaDTO pruebaDTO = pruebaMapper.pruebaToPruebaDTO(prueba);

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate + 1);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testPrueba.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();

        // Create the Prueba with an existing ID
        Prueba existingPrueba = new Prueba();
        existingPrueba.setId(1L);
        PruebaDTO existingPruebaDTO = pruebaMapper.pruebaToPruebaDTO(existingPrueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBodyIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setBody(null);

        // Create the Prueba, which fails.
        PruebaDTO pruebaDTO = pruebaMapper.pruebaToPruebaDTO(prueba);

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebaDTO)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPruebas() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);

        // Get all the pruebaList
        restPruebaMockMvc.perform(get("/api/pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prueba.getId().intValue())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrueba() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);

        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prueba.getId().intValue()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrueba() throws Exception {
        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrueba() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);
        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // Update the prueba
        Prueba updatedPrueba = pruebaRepository.findOne(prueba.getId());
        updatedPrueba
                .body(UPDATED_BODY)
                .activo(UPDATED_ACTIVO);
        PruebaDTO pruebaDTO = pruebaMapper.pruebaToPruebaDTO(updatedPrueba);

        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebaDTO)))
            .andExpect(status().isOk());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testPrueba.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingPrueba() throws Exception {
        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // Create the Prueba
        PruebaDTO pruebaDTO = pruebaMapper.pruebaToPruebaDTO(prueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrueba() throws Exception {
        // Initialize the database
        pruebaRepository.saveAndFlush(prueba);
        int databaseSizeBeforeDelete = pruebaRepository.findAll().size();

        // Get the prueba
        restPruebaMockMvc.perform(delete("/api/pruebas/{id}", prueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prueba> pruebaList = pruebaRepository.findByActivoTrue();
        assertThat(pruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
