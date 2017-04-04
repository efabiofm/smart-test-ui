package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Metodo;
import com.quenti.smarttestui.repository.MetodoRepository;
import com.quenti.smarttestui.service.MetodoService;
import com.quenti.smarttestui.service.dto.MetodoDTO;
import com.quenti.smarttestui.service.mapper.MetodoMapper;

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
 * Test class for the MetodoResource REST controller.
 *
 * @see MetodoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class MetodoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private MetodoRepository metodoRepository;

    @Inject
    private MetodoMapper metodoMapper;

    @Inject
    private MetodoService metodoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMetodoMockMvc;

    private Metodo metodo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MetodoResource metodoResource = new MetodoResource();
        ReflectionTestUtils.setField(metodoResource, "metodoService", metodoService);
        this.restMetodoMockMvc = MockMvcBuilders.standaloneSetup(metodoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Metodo createEntity(EntityManager em) {
        Metodo metodo = new Metodo()
                .nombre(DEFAULT_NOMBRE)
                .url(DEFAULT_URL)
                .activo(DEFAULT_ACTIVO);
        return metodo;
    }

    @Before
    public void initTest() {
        metodo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMetodo() throws Exception {
        int databaseSizeBeforeCreate = metodoRepository.findAll().size();

        // Create the Metodo
        MetodoDTO metodoDTO = metodoMapper.metodoToMetodoDTO(metodo);

        restMetodoMockMvc.perform(post("/api/metodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metodoDTO)))
            .andExpect(status().isCreated());

        // Validate the Metodo in the database
        List<Metodo> metodoList = metodoRepository.findAll();
        assertThat(metodoList).hasSize(databaseSizeBeforeCreate + 1);
        Metodo testMetodo = metodoList.get(metodoList.size() - 1);
        assertThat(testMetodo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMetodo.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testMetodo.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createMetodoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = metodoRepository.findAll().size();

        // Create the Metodo with an existing ID
        Metodo existingMetodo = new Metodo();
        existingMetodo.setId(1L);
        MetodoDTO existingMetodoDTO = metodoMapper.metodoToMetodoDTO(existingMetodo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetodoMockMvc.perform(post("/api/metodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMetodoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Metodo> metodoList = metodoRepository.findAll();
        assertThat(metodoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = metodoRepository.findAll().size();
        // set the field null
        metodo.setNombre(null);

        // Create the Metodo, which fails.
        MetodoDTO metodoDTO = metodoMapper.metodoToMetodoDTO(metodo);

        restMetodoMockMvc.perform(post("/api/metodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metodoDTO)))
            .andExpect(status().isBadRequest());

        List<Metodo> metodoList = metodoRepository.findAll();
        assertThat(metodoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = metodoRepository.findAll().size();
        // set the field null
        metodo.setUrl(null);

        // Create the Metodo, which fails.
        MetodoDTO metodoDTO = metodoMapper.metodoToMetodoDTO(metodo);

        restMetodoMockMvc.perform(post("/api/metodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metodoDTO)))
            .andExpect(status().isBadRequest());

        List<Metodo> metodoList = metodoRepository.findAll();
        assertThat(metodoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMetodos() throws Exception {
        // Initialize the database
        metodoRepository.saveAndFlush(metodo);

        // Get all the metodoList
        restMetodoMockMvc.perform(get("/api/metodos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metodo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getMetodo() throws Exception {
        // Initialize the database
        metodoRepository.saveAndFlush(metodo);

        // Get the metodo
        restMetodoMockMvc.perform(get("/api/metodos/{id}", metodo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(metodo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMetodo() throws Exception {
        // Get the metodo
        restMetodoMockMvc.perform(get("/api/metodos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMetodo() throws Exception {
        // Initialize the database
        metodoRepository.saveAndFlush(metodo);
        int databaseSizeBeforeUpdate = metodoRepository.findAll().size();

        // Update the metodo
        Metodo updatedMetodo = metodoRepository.findOne(metodo.getId());
        updatedMetodo
                .nombre(UPDATED_NOMBRE)
                .url(UPDATED_URL)
                .activo(UPDATED_ACTIVO);
        MetodoDTO metodoDTO = metodoMapper.metodoToMetodoDTO(updatedMetodo);

        restMetodoMockMvc.perform(put("/api/metodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metodoDTO)))
            .andExpect(status().isOk());

        // Validate the Metodo in the database
        List<Metodo> metodoList = metodoRepository.findAll();
        assertThat(metodoList).hasSize(databaseSizeBeforeUpdate);
        Metodo testMetodo = metodoList.get(metodoList.size() - 1);
        assertThat(testMetodo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMetodo.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testMetodo.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingMetodo() throws Exception {
        int databaseSizeBeforeUpdate = metodoRepository.findAll().size();

        // Create the Metodo
        MetodoDTO metodoDTO = metodoMapper.metodoToMetodoDTO(metodo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMetodoMockMvc.perform(put("/api/metodos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metodoDTO)))
            .andExpect(status().isCreated());

        // Validate the Metodo in the database
        List<Metodo> metodoList = metodoRepository.findAll();
        assertThat(metodoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMetodo() throws Exception {
        // Initialize the database
        metodoRepository.saveAndFlush(metodo);
        int databaseSizeBeforeDelete = metodoRepository.findAll().size();

        // Get the metodo
        restMetodoMockMvc.perform(delete("/api/metodos/{id}", metodo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Metodo> metodoList = metodoRepository.findAll();
        assertThat(metodoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
