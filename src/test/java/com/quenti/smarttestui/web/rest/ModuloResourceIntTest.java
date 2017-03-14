package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Modulo;
import com.quenti.smarttestui.repository.ModuloRepository;
import com.quenti.smarttestui.service.ModuloService;
import com.quenti.smarttestui.service.dto.ModuloDTO;
import com.quenti.smarttestui.service.mapper.ModuloMapper;

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
 * Test class for the ModuloResource REST controller.
 *
 * @see ModuloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class ModuloResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private ModuloRepository moduloRepository;

    @Inject
    private ModuloMapper moduloMapper;

    @Inject
    private ModuloService moduloService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restModuloMockMvc;

    private Modulo modulo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModuloResource moduloResource = new ModuloResource();
        ReflectionTestUtils.setField(moduloResource, "moduloService", moduloService);
        this.restModuloMockMvc = MockMvcBuilders.standaloneSetup(moduloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modulo createEntity(EntityManager em) {
        Modulo modulo = new Modulo()
                .nombre(DEFAULT_NOMBRE)
                .url(DEFAULT_URL)
                .activo(DEFAULT_ACTIVO);
        return modulo;
    }

    @Before
    public void initTest() {
        modulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createModulo() throws Exception {
        int databaseSizeBeforeCreate = moduloRepository.findAll().size();

        // Create the Modulo
        ModuloDTO moduloDTO = moduloMapper.moduloToModuloDTO(modulo);

        restModuloMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduloDTO)))
            .andExpect(status().isCreated());

        // Validate the Modulo in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeCreate + 1);
        Modulo testModulo = moduloList.get(moduloList.size() - 1);
        assertThat(testModulo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testModulo.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testModulo.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createModuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduloRepository.findAll().size();

        // Create the Modulo with an existing ID
        Modulo existingModulo = new Modulo();
        existingModulo.setId(1L);
        ModuloDTO existingModuloDTO = moduloMapper.moduloToModuloDTO(existingModulo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuloMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingModuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduloRepository.findAll().size();
        // set the field null
        modulo.setNombre(null);

        // Create the Modulo, which fails.
        ModuloDTO moduloDTO = moduloMapper.moduloToModuloDTO(modulo);

        restModuloMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduloDTO)))
            .andExpect(status().isBadRequest());

        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduloRepository.findAll().size();
        // set the field null
        modulo.setUrl(null);

        // Create the Modulo, which fails.
        ModuloDTO moduloDTO = moduloMapper.moduloToModuloDTO(modulo);

        restModuloMockMvc.perform(post("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduloDTO)))
            .andExpect(status().isBadRequest());

        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModulos() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList
        restModuloMockMvc.perform(get("/api/modulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getModulo() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);

        // Get the modulo
        restModuloMockMvc.perform(get("/api/modulos/{id}", modulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modulo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModulo() throws Exception {
        // Get the modulo
        restModuloMockMvc.perform(get("/api/modulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModulo() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);
        int databaseSizeBeforeUpdate = moduloRepository.findAll().size();

        // Update the modulo
        Modulo updatedModulo = moduloRepository.findOne(modulo.getId());
        updatedModulo
                .nombre(UPDATED_NOMBRE)
                .url(UPDATED_URL)
                .activo(UPDATED_ACTIVO);
        ModuloDTO moduloDTO = moduloMapper.moduloToModuloDTO(updatedModulo);

        restModuloMockMvc.perform(put("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduloDTO)))
            .andExpect(status().isOk());

        // Validate the Modulo in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeUpdate);
        Modulo testModulo = moduloList.get(moduloList.size() - 1);
        assertThat(testModulo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testModulo.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testModulo.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingModulo() throws Exception {
        int databaseSizeBeforeUpdate = moduloRepository.findAll().size();

        // Create the Modulo
        ModuloDTO moduloDTO = moduloMapper.moduloToModuloDTO(modulo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModuloMockMvc.perform(put("/api/modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduloDTO)))
            .andExpect(status().isCreated());

        // Validate the Modulo in the database
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModulo() throws Exception {
        // Initialize the database
        moduloRepository.saveAndFlush(modulo);
        int databaseSizeBeforeDelete = moduloRepository.findAll().size();

        // Get the modulo
        restModuloMockMvc.perform(delete("/api/modulos/{id}", modulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Modulo> moduloList = moduloRepository.findAll();
        assertThat(moduloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
