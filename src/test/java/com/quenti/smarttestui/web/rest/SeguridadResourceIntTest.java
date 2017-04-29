package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Seguridad;
import com.quenti.smarttestui.repository.SeguridadRepository;
import com.quenti.smarttestui.service.SeguridadService;
import com.quenti.smarttestui.service.dto.SeguridadDTO;
import com.quenti.smarttestui.service.mapper.SeguridadMapper;

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
 * Test class for the SeguridadResource REST controller.
 *
 * @see SeguridadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class SeguridadResourceIntTest {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_JH_USER_ID = new Long(1);
    private static final Long UPDATED_JH_USER_ID = new Long(2);

    @Inject
    private SeguridadRepository seguridadRepository;

    @Inject
    private SeguridadMapper seguridadMapper;

    @Inject
    private SeguridadService seguridadService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSeguridadMockMvc;

    private Seguridad seguridad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeguridadResource seguridadResource = new SeguridadResource();
        ReflectionTestUtils.setField(seguridadResource, "seguridadService", seguridadService);
        this.restSeguridadMockMvc = MockMvcBuilders.standaloneSetup(seguridadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seguridad createEntity(EntityManager em) {
        Seguridad seguridad = new Seguridad()
                .token(DEFAULT_TOKEN)
                .fecha(DEFAULT_FECHA)
                .jhUserId(DEFAULT_JH_USER_ID);
        return seguridad;
    }

    @Before
    public void initTest() {
        seguridad = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeguridad() throws Exception {
        int databaseSizeBeforeCreate = seguridadRepository.findAll().size();

        // Create the Seguridad
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(seguridad);

        restSeguridadMockMvc.perform(post("/api/seguridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguridadDTO)))
            .andExpect(status().isCreated());

        // Validate the Seguridad in the database
        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeCreate + 1);
        Seguridad testSeguridad = seguridadList.get(seguridadList.size() - 1);
        assertThat(testSeguridad.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testSeguridad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testSeguridad.getJhUserId()).isEqualTo(DEFAULT_JH_USER_ID);
    }

    @Test
    @Transactional
    public void createSeguridadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seguridadRepository.findAll().size();

        // Create the Seguridad with an existing ID
        Seguridad existingSeguridad = new Seguridad();
        existingSeguridad.setId(1L);
        SeguridadDTO existingSeguridadDTO = seguridadMapper.seguridadToSeguridadDTO(existingSeguridad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguridadMockMvc.perform(post("/api/seguridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSeguridadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguridadRepository.findAll().size();
        // set the field null
        seguridad.setToken(null);

        // Create the Seguridad, which fails.
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(seguridad);

        restSeguridadMockMvc.perform(post("/api/seguridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguridadDTO)))
            .andExpect(status().isBadRequest());

        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguridadRepository.findAll().size();
        // set the field null
        seguridad.setFecha(null);

        // Create the Seguridad, which fails.
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(seguridad);

        restSeguridadMockMvc.perform(post("/api/seguridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguridadDTO)))
            .andExpect(status().isBadRequest());

        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJhUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = seguridadRepository.findAll().size();
        // set the field null
        seguridad.setJhUserId(null);

        // Create the Seguridad, which fails.
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(seguridad);

        restSeguridadMockMvc.perform(post("/api/seguridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguridadDTO)))
            .andExpect(status().isBadRequest());

        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeguridads() throws Exception {
        // Initialize the database
        seguridadRepository.saveAndFlush(seguridad);

        // Get all the seguridadList
        restSeguridadMockMvc.perform(get("/api/seguridads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguridad.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].jhUserId").value(hasItem(DEFAULT_JH_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSeguridad() throws Exception {
        // Initialize the database
        seguridadRepository.saveAndFlush(seguridad);

        // Get the seguridad
        restSeguridadMockMvc.perform(get("/api/seguridads/{id}", seguridad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seguridad.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.jhUserId").value(DEFAULT_JH_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSeguridad() throws Exception {
        // Get the seguridad
        restSeguridadMockMvc.perform(get("/api/seguridads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeguridad() throws Exception {
        // Initialize the database
        seguridadRepository.saveAndFlush(seguridad);
        int databaseSizeBeforeUpdate = seguridadRepository.findAll().size();

        // Update the seguridad
        Seguridad updatedSeguridad = seguridadRepository.findOne(seguridad.getId());
        updatedSeguridad
                .token(UPDATED_TOKEN)
                .fecha(UPDATED_FECHA)
                .jhUserId(UPDATED_JH_USER_ID);
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(updatedSeguridad);

        restSeguridadMockMvc.perform(put("/api/seguridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguridadDTO)))
            .andExpect(status().isOk());

        // Validate the Seguridad in the database
        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeUpdate);
        Seguridad testSeguridad = seguridadList.get(seguridadList.size() - 1);
        assertThat(testSeguridad.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testSeguridad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testSeguridad.getJhUserId()).isEqualTo(UPDATED_JH_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSeguridad() throws Exception {
        int databaseSizeBeforeUpdate = seguridadRepository.findAll().size();

        // Create the Seguridad
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(seguridad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeguridadMockMvc.perform(put("/api/seguridads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seguridadDTO)))
            .andExpect(status().isCreated());

        // Validate the Seguridad in the database
        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeguridad() throws Exception {
        // Initialize the database
        seguridadRepository.saveAndFlush(seguridad);
        int databaseSizeBeforeDelete = seguridadRepository.findAll().size();

        // Get the seguridad
        restSeguridadMockMvc.perform(delete("/api/seguridads/{id}", seguridad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seguridad> seguridadList = seguridadRepository.findAll();
        assertThat(seguridadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
