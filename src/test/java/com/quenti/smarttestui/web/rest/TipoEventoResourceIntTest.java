package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.TipoEvento;
import com.quenti.smarttestui.repository.TipoEventoRepository;
import com.quenti.smarttestui.service.TipoEventoService;
import com.quenti.smarttestui.service.dto.TipoEventoDTO;
import com.quenti.smarttestui.service.mapper.TipoEventoMapper;

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
 * Test class for the TipoEventoResource REST controller.
 *
 * @see TipoEventoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class TipoEventoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = true;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private TipoEventoRepository tipoEventoRepository;

    @Inject
    private TipoEventoMapper tipoEventoMapper;

    @Inject
    private TipoEventoService tipoEventoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTipoEventoMockMvc;

    private TipoEvento tipoEvento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoEventoResource tipoEventoResource = new TipoEventoResource();
        ReflectionTestUtils.setField(tipoEventoResource, "tipoEventoService", tipoEventoService);
        this.restTipoEventoMockMvc = MockMvcBuilders.standaloneSetup(tipoEventoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEvento createEntity(EntityManager em) {
        TipoEvento tipoEvento = new TipoEvento()
                .nombre(DEFAULT_NOMBRE)
                .activo(DEFAULT_ACTIVO);
        return tipoEvento;
    }

    @Before
    public void initTest() {
        tipoEvento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEvento() throws Exception {
        int databaseSizeBeforeCreate = tipoEventoRepository.findAll().size();

        // Create the TipoEvento
        TipoEventoDTO tipoEventoDTO = tipoEventoMapper.tipoEventoToTipoEventoDTO(tipoEvento);

        restTipoEventoMockMvc.perform(post("/api/tipo-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEventoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoEvento in the database
        List<TipoEvento> tipoEventoList = tipoEventoRepository.findAll();
        assertThat(tipoEventoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEvento testTipoEvento = tipoEventoList.get(tipoEventoList.size() - 1);
        assertThat(testTipoEvento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoEvento.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createTipoEventoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEventoRepository.findAll().size();

        // Create the TipoEvento with an existing ID
        TipoEvento existingTipoEvento = new TipoEvento();
        existingTipoEvento.setId(1L);
        TipoEventoDTO existingTipoEventoDTO = tipoEventoMapper.tipoEventoToTipoEventoDTO(existingTipoEvento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEventoMockMvc.perform(post("/api/tipo-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTipoEventoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TipoEvento> tipoEventoList = tipoEventoRepository.findAll();
        assertThat(tipoEventoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEventoRepository.findAll().size();
        // set the field null
        tipoEvento.setNombre(null);

        // Create the TipoEvento, which fails.
        TipoEventoDTO tipoEventoDTO = tipoEventoMapper.tipoEventoToTipoEventoDTO(tipoEvento);

        restTipoEventoMockMvc.perform(post("/api/tipo-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEventoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoEvento> tipoEventoList = tipoEventoRepository.findAll();
        assertThat(tipoEventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoEventos() throws Exception {
        // Initialize the database
        tipoEventoRepository.saveAndFlush(tipoEvento);

        // Get all the tipoEventoList
        restTipoEventoMockMvc.perform(get("/api/tipo-eventos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEvento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTipoEvento() throws Exception {
        // Initialize the database
        tipoEventoRepository.saveAndFlush(tipoEvento);

        // Get the tipoEvento
        restTipoEventoMockMvc.perform(get("/api/tipo-eventos/{id}", tipoEvento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEvento.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoEvento() throws Exception {
        // Get the tipoEvento
        restTipoEventoMockMvc.perform(get("/api/tipo-eventos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEvento() throws Exception {
        // Initialize the database
        tipoEventoRepository.saveAndFlush(tipoEvento);
        int databaseSizeBeforeUpdate = tipoEventoRepository.findAll().size();

        // Update the tipoEvento
        TipoEvento updatedTipoEvento = tipoEventoRepository.findOne(tipoEvento.getId());
        updatedTipoEvento
                .nombre(UPDATED_NOMBRE)
                .activo(UPDATED_ACTIVO);
        TipoEventoDTO tipoEventoDTO = tipoEventoMapper.tipoEventoToTipoEventoDTO(updatedTipoEvento);

        restTipoEventoMockMvc.perform(put("/api/tipo-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEventoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoEvento in the database
        List<TipoEvento> tipoEventoList = tipoEventoRepository.findAll();
        assertThat(tipoEventoList).hasSize(databaseSizeBeforeUpdate);
        TipoEvento testTipoEvento = tipoEventoList.get(tipoEventoList.size() - 1);
        assertThat(testTipoEvento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoEvento.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEvento() throws Exception {
        int databaseSizeBeforeUpdate = tipoEventoRepository.findAll().size();

        // Create the TipoEvento
        TipoEventoDTO tipoEventoDTO = tipoEventoMapper.tipoEventoToTipoEventoDTO(tipoEvento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoEventoMockMvc.perform(put("/api/tipo-eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEventoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoEvento in the database
        List<TipoEvento> tipoEventoList = tipoEventoRepository.findAll();
        assertThat(tipoEventoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoEvento() throws Exception {
        // Initialize the database
        tipoEventoRepository.saveAndFlush(tipoEvento);
        int databaseSizeBeforeDelete = tipoEventoRepository.findAll().size();

        // Get the tipoEvento
        restTipoEventoMockMvc.perform(delete("/api/tipo-eventos/{id}", tipoEvento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoEvento> tipoEventoList = tipoEventoRepository.findByActivoTrue();
        assertThat(tipoEventoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
