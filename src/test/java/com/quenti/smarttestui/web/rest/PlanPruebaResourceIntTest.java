package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.PlanPrueba;
import com.quenti.smarttestui.repository.PlanPruebaRepository;
import com.quenti.smarttestui.service.PlanPruebaService;
import com.quenti.smarttestui.service.dto.PlanPruebaDTO;
import com.quenti.smarttestui.service.mapper.PlanPruebaMapper;

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
 * Test class for the PlanPruebaResource REST controller.
 *
 * @see PlanPruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class PlanPruebaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private PlanPruebaRepository planPruebaRepository;

    @Inject
    private PlanPruebaMapper planPruebaMapper;

    @Inject
    private PlanPruebaService planPruebaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPlanPruebaMockMvc;

    private PlanPrueba planPrueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlanPruebaResource planPruebaResource = new PlanPruebaResource();
        ReflectionTestUtils.setField(planPruebaResource, "planPruebaService", planPruebaService);
        this.restPlanPruebaMockMvc = MockMvcBuilders.standaloneSetup(planPruebaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanPrueba createEntity(EntityManager em) {
        PlanPrueba planPrueba = new PlanPrueba()
                .nombre(DEFAULT_NOMBRE)
                .activo(DEFAULT_ACTIVO);
        return planPrueba;
    }

    @Before
    public void initTest() {
        planPrueba = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanPrueba() throws Exception {
        int databaseSizeBeforeCreate = planPruebaRepository.findAll().size();

        // Create the PlanPrueba
        PlanPruebaDTO planPruebaDTO = planPruebaMapper.planPruebaToPlanPruebaDTO(planPrueba);

        restPlanPruebaMockMvc.perform(post("/api/plan-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanPrueba in the database
        List<PlanPrueba> planPruebaList = planPruebaRepository.findAll();
        assertThat(planPruebaList).hasSize(databaseSizeBeforeCreate + 1);
        PlanPrueba testPlanPrueba = planPruebaList.get(planPruebaList.size() - 1);
        assertThat(testPlanPrueba.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPlanPrueba.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createPlanPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planPruebaRepository.findAll().size();

        // Create the PlanPrueba with an existing ID
        PlanPrueba existingPlanPrueba = new PlanPrueba();
        existingPlanPrueba.setId(1L);
        PlanPruebaDTO existingPlanPruebaDTO = planPruebaMapper.planPruebaToPlanPruebaDTO(existingPlanPrueba);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanPruebaMockMvc.perform(post("/api/plan-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPlanPruebaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PlanPrueba> planPruebaList = planPruebaRepository.findAll();
        assertThat(planPruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = planPruebaRepository.findAll().size();
        // set the field null
        planPrueba.setNombre(null);

        // Create the PlanPrueba, which fails.
        PlanPruebaDTO planPruebaDTO = planPruebaMapper.planPruebaToPlanPruebaDTO(planPrueba);

        restPlanPruebaMockMvc.perform(post("/api/plan-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planPruebaDTO)))
            .andExpect(status().isBadRequest());

        List<PlanPrueba> planPruebaList = planPruebaRepository.findAll();
        assertThat(planPruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanPruebas() throws Exception {
        // Initialize the database
        planPruebaRepository.saveAndFlush(planPrueba);

        // Get all the planPruebaList
        restPlanPruebaMockMvc.perform(get("/api/plan-pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planPrueba.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPlanPrueba() throws Exception {
        // Initialize the database
        planPruebaRepository.saveAndFlush(planPrueba);

        // Get the planPrueba
        restPlanPruebaMockMvc.perform(get("/api/plan-pruebas/{id}", planPrueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planPrueba.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanPrueba() throws Exception {
        // Get the planPrueba
        restPlanPruebaMockMvc.perform(get("/api/plan-pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanPrueba() throws Exception {
        // Initialize the database
        planPruebaRepository.saveAndFlush(planPrueba);
        int databaseSizeBeforeUpdate = planPruebaRepository.findAll().size();

        // Update the planPrueba
        PlanPrueba updatedPlanPrueba = planPruebaRepository.findOne(planPrueba.getId());
        updatedPlanPrueba
                .nombre(UPDATED_NOMBRE)
                .activo(UPDATED_ACTIVO);
        PlanPruebaDTO planPruebaDTO = planPruebaMapper.planPruebaToPlanPruebaDTO(updatedPlanPrueba);

        restPlanPruebaMockMvc.perform(put("/api/plan-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planPruebaDTO)))
            .andExpect(status().isOk());

        // Validate the PlanPrueba in the database
        List<PlanPrueba> planPruebaList = planPruebaRepository.findAll();
        assertThat(planPruebaList).hasSize(databaseSizeBeforeUpdate);
        PlanPrueba testPlanPrueba = planPruebaList.get(planPruebaList.size() - 1);
        assertThat(testPlanPrueba.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPlanPrueba.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanPrueba() throws Exception {
        int databaseSizeBeforeUpdate = planPruebaRepository.findAll().size();

        // Create the PlanPrueba
        PlanPruebaDTO planPruebaDTO = planPruebaMapper.planPruebaToPlanPruebaDTO(planPrueba);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanPruebaMockMvc.perform(put("/api/plan-pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planPruebaDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanPrueba in the database
        List<PlanPrueba> planPruebaList = planPruebaRepository.findAll();
        assertThat(planPruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlanPrueba() throws Exception {
        // Initialize the database
        planPruebaRepository.saveAndFlush(planPrueba);
        int databaseSizeBeforeDelete = planPruebaRepository.findAll().size();

        // Get the planPrueba
        restPlanPruebaMockMvc.perform(delete("/api/plan-pruebas/{id}", planPrueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanPrueba> planPruebaList = planPruebaRepository.findAll();
        assertThat(planPruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
