package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Ambiente;
import com.quenti.smarttestui.repository.AmbienteRepository;
import com.quenti.smarttestui.service.AmbienteService;
import com.quenti.smarttestui.service.dto.AmbienteDTO;
import com.quenti.smarttestui.service.mapper.AmbienteMapper;

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
 * Test class for the AmbienteResource REST controller.
 *
 * @see AmbienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class AmbienteResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = true;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private AmbienteRepository ambienteRepository;

    @Inject
    private AmbienteMapper ambienteMapper;

    @Inject
    private AmbienteService ambienteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAmbienteMockMvc;

    private Ambiente ambiente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AmbienteResource ambienteResource = new AmbienteResource();
        ReflectionTestUtils.setField(ambienteResource, "ambienteService", ambienteService);
        this.restAmbienteMockMvc = MockMvcBuilders.standaloneSetup(ambienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ambiente createEntity(EntityManager em) {
        Ambiente ambiente = new Ambiente()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION)
                .activo(DEFAULT_ACTIVO);
        return ambiente;
    }

    @Before
    public void initTest() {
        ambiente = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmbiente() throws Exception {
        int databaseSizeBeforeCreate = ambienteRepository.findAll().size();

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.ambienteToAmbienteDTO(ambiente);

        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeCreate + 1);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAmbiente.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAmbiente.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createAmbienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ambienteRepository.findAll().size();

        // Create the Ambiente with an existing ID
        Ambiente existingAmbiente = new Ambiente();
        existingAmbiente.setId(1L);
        AmbienteDTO existingAmbienteDTO = ambienteMapper.ambienteToAmbienteDTO(existingAmbiente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAmbienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = ambienteRepository.findAll().size();
        // set the field null
        ambiente.setNombre(null);

        // Create the Ambiente, which fails.
        AmbienteDTO ambienteDTO = ambienteMapper.ambienteToAmbienteDTO(ambiente);

        restAmbienteMockMvc.perform(post("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isBadRequest());

        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmbientes() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList
        restAmbienteMockMvc.perform(get("/api/ambientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ambiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get the ambiente
        restAmbienteMockMvc.perform(get("/api/ambientes/{id}", ambiente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ambiente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAmbiente() throws Exception {
        // Get the ambiente
        restAmbienteMockMvc.perform(get("/api/ambientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();

        // Update the ambiente
        Ambiente updatedAmbiente = ambienteRepository.findOne(ambiente.getId());
        updatedAmbiente
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION)
                .activo(UPDATED_ACTIVO);
        AmbienteDTO ambienteDTO = ambienteMapper.ambienteToAmbienteDTO(updatedAmbiente);

        restAmbienteMockMvc.perform(put("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isOk());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAmbiente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAmbiente.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.ambienteToAmbienteDTO(ambiente);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAmbienteMockMvc.perform(put("/api/ambientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);
        int databaseSizeBeforeDelete = ambienteRepository.findAll().size();

        // Get the ambiente
        restAmbienteMockMvc.perform(delete("/api/ambientes/{id}", ambiente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ambiente> ambienteList = ambienteRepository.findByActivoTrue();
        assertThat(ambienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
