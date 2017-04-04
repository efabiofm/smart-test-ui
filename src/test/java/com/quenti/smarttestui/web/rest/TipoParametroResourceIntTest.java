package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.TipoParametro;
import com.quenti.smarttestui.repository.TipoParametroRepository;
import com.quenti.smarttestui.service.TipoParametroService;
import com.quenti.smarttestui.service.dto.TipoParametroDTO;
import com.quenti.smarttestui.service.mapper.TipoParametroMapper;

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
 * Test class for the TipoParametroResource REST controller.
 *
 * @see TipoParametroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class TipoParametroResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private TipoParametroRepository tipoParametroRepository;

    @Inject
    private TipoParametroMapper tipoParametroMapper;

    @Inject
    private TipoParametroService tipoParametroService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTipoParametroMockMvc;

    private TipoParametro tipoParametro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoParametroResource tipoParametroResource = new TipoParametroResource();
        ReflectionTestUtils.setField(tipoParametroResource, "tipoParametroService", tipoParametroService);
        this.restTipoParametroMockMvc = MockMvcBuilders.standaloneSetup(tipoParametroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoParametro createEntity(EntityManager em) {
        TipoParametro tipoParametro = new TipoParametro()
                .nombre(DEFAULT_NOMBRE)
                .activo(DEFAULT_ACTIVO);
        return tipoParametro;
    }

    @Before
    public void initTest() {
        tipoParametro = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoParametro() throws Exception {
        int databaseSizeBeforeCreate = tipoParametroRepository.findAll().size();

        // Create the TipoParametro
        TipoParametroDTO tipoParametroDTO = tipoParametroMapper.tipoParametroToTipoParametroDTO(tipoParametro);

        restTipoParametroMockMvc.perform(post("/api/tipo-parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoParametroDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoParametro in the database
        List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
        assertThat(tipoParametroList).hasSize(databaseSizeBeforeCreate + 1);
        TipoParametro testTipoParametro = tipoParametroList.get(tipoParametroList.size() - 1);
        assertThat(testTipoParametro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoParametro.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createTipoParametroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoParametroRepository.findAll().size();

        // Create the TipoParametro with an existing ID
        TipoParametro existingTipoParametro = new TipoParametro();
        existingTipoParametro.setId(1L);
        TipoParametroDTO existingTipoParametroDTO = tipoParametroMapper.tipoParametroToTipoParametroDTO(existingTipoParametro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoParametroMockMvc.perform(post("/api/tipo-parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTipoParametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
        assertThat(tipoParametroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoParametroRepository.findAll().size();
        // set the field null
        tipoParametro.setNombre(null);

        // Create the TipoParametro, which fails.
        TipoParametroDTO tipoParametroDTO = tipoParametroMapper.tipoParametroToTipoParametroDTO(tipoParametro);

        restTipoParametroMockMvc.perform(post("/api/tipo-parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoParametroDTO)))
            .andExpect(status().isBadRequest());

        List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
        assertThat(tipoParametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoParametros() throws Exception {
        // Initialize the database
        tipoParametroRepository.saveAndFlush(tipoParametro);

        // Get all the tipoParametroList
        restTipoParametroMockMvc.perform(get("/api/tipo-parametros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoParametro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTipoParametro() throws Exception {
        // Initialize the database
        tipoParametroRepository.saveAndFlush(tipoParametro);

        // Get the tipoParametro
        restTipoParametroMockMvc.perform(get("/api/tipo-parametros/{id}", tipoParametro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoParametro.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoParametro() throws Exception {
        // Get the tipoParametro
        restTipoParametroMockMvc.perform(get("/api/tipo-parametros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoParametro() throws Exception {
        // Initialize the database
        tipoParametroRepository.saveAndFlush(tipoParametro);
        int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();

        // Update the tipoParametro
        TipoParametro updatedTipoParametro = tipoParametroRepository.findOne(tipoParametro.getId());
        updatedTipoParametro
                .nombre(UPDATED_NOMBRE)
                .activo(UPDATED_ACTIVO);
        TipoParametroDTO tipoParametroDTO = tipoParametroMapper.tipoParametroToTipoParametroDTO(updatedTipoParametro);

        restTipoParametroMockMvc.perform(put("/api/tipo-parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoParametroDTO)))
            .andExpect(status().isOk());

        // Validate the TipoParametro in the database
        List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
        assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate);
        TipoParametro testTipoParametro = tipoParametroList.get(tipoParametroList.size() - 1);
        assertThat(testTipoParametro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoParametro.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoParametro() throws Exception {
        int databaseSizeBeforeUpdate = tipoParametroRepository.findAll().size();

        // Create the TipoParametro
        TipoParametroDTO tipoParametroDTO = tipoParametroMapper.tipoParametroToTipoParametroDTO(tipoParametro);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoParametroMockMvc.perform(put("/api/tipo-parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoParametroDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoParametro in the database
        List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
        assertThat(tipoParametroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoParametro() throws Exception {
        // Initialize the database
        tipoParametroRepository.saveAndFlush(tipoParametro);
        int databaseSizeBeforeDelete = tipoParametroRepository.findAll().size();

        // Get the tipoParametro
        restTipoParametroMockMvc.perform(delete("/api/tipo-parametros/{id}", tipoParametro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
        assertThat(tipoParametroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
