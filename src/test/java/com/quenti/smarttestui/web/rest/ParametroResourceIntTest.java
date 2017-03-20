package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Parametro;
import com.quenti.smarttestui.repository.ParametroRepository;
import com.quenti.smarttestui.service.ParametroService;
import com.quenti.smarttestui.service.dto.ParametroDTO;
import com.quenti.smarttestui.service.mapper.ParametroMapper;

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
 * Test class for the ParametroResource REST controller.
 *
 * @see ParametroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class ParametroResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private ParametroRepository parametroRepository;

    @Inject
    private ParametroMapper parametroMapper;

    @Inject
    private ParametroService parametroService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restParametroMockMvc;

    private Parametro parametro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParametroResource parametroResource = new ParametroResource();
        ReflectionTestUtils.setField(parametroResource, "parametroService", parametroService);
        this.restParametroMockMvc = MockMvcBuilders.standaloneSetup(parametroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parametro createEntity(EntityManager em) {
        Parametro parametro = new Parametro()
                .nombre(DEFAULT_NOMBRE)
                .activo(DEFAULT_ACTIVO);
        return parametro;
    }

    @Before
    public void initTest() {
        parametro = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametro() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.parametroToParametroDTO(parametro);

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isCreated());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate + 1);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testParametro.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createParametroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro with an existing ID
        Parametro existingParametro = new Parametro();
        existingParametro.setId(1L);
        ParametroDTO existingParametroDTO = parametroMapper.parametroToParametroDTO(existingParametro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingParametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setNombre(null);

        // Create the Parametro, which fails.
        ParametroDTO parametroDTO = parametroMapper.parametroToParametroDTO(parametro);

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametros() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList
        restParametroMockMvc.perform(get("/api/parametros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", parametro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametro.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParametro() throws Exception {
        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);
        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Update the parametro
        Parametro updatedParametro = parametroRepository.findOne(parametro.getId());
        updatedParametro
                .nombre(UPDATED_NOMBRE)
                .activo(UPDATED_ACTIVO);
        ParametroDTO parametroDTO = parametroMapper.parametroToParametroDTO(updatedParametro);

        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isOk());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testParametro.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingParametro() throws Exception {
        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.parametroToParametroDTO(parametro);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isCreated());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);
        int databaseSizeBeforeDelete = parametroRepository.findAll().size();

        // Get the parametro
        restParametroMockMvc.perform(delete("/api/parametros/{id}", parametro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
