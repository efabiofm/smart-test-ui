package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.Bitacora;
import com.quenti.smarttestui.repository.BitacoraRepository;
import com.quenti.smarttestui.service.BitacoraService;
import com.quenti.smarttestui.service.dto.BitacoraDTO;
import com.quenti.smarttestui.service.mapper.BitacoraMapper;

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
 * Test class for the BitacoraResource REST controller.
 *
 * @see BitacoraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class BitacoraResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_JH_USER_ID = 1;
    private static final Integer UPDATED_JH_USER_ID = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Inject
    private BitacoraRepository bitacoraRepository;

    @Inject
    private BitacoraMapper bitacoraMapper;

    @Inject
    private BitacoraService bitacoraService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBitacoraMockMvc;

    private Bitacora bitacora;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BitacoraResource bitacoraResource = new BitacoraResource();
        ReflectionTestUtils.setField(bitacoraResource, "bitacoraService", bitacoraService);
        this.restBitacoraMockMvc = MockMvcBuilders.standaloneSetup(bitacoraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bitacora createEntity(EntityManager em) {
        Bitacora bitacora = new Bitacora()
                .fecha(DEFAULT_FECHA)
                .jhUserId(DEFAULT_JH_USER_ID)
                .descripcion(DEFAULT_DESCRIPCION);
        return bitacora;
    }

    @Before
    public void initTest() {
        bitacora = createEntity(em);
    }

    @Test
    @Transactional
    public void createBitacora() throws Exception {
        int databaseSizeBeforeCreate = bitacoraRepository.findAll().size();

        // Create the Bitacora
        BitacoraDTO bitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(bitacora);

        restBitacoraMockMvc.perform(post("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isCreated());

        // Validate the Bitacora in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeCreate + 1);
        Bitacora testBitacora = bitacoraList.get(bitacoraList.size() - 1);
        assertThat(testBitacora.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testBitacora.getJhUserId()).isEqualTo(DEFAULT_JH_USER_ID);
        assertThat(testBitacora.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createBitacoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bitacoraRepository.findAll().size();

        // Create the Bitacora with an existing ID
        Bitacora existingBitacora = new Bitacora();
        existingBitacora.setId(1L);
        BitacoraDTO existingBitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(existingBitacora);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBitacoraMockMvc.perform(post("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBitacoraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitacoraRepository.findAll().size();
        // set the field null
        bitacora.setFecha(null);

        // Create the Bitacora, which fails.
        BitacoraDTO bitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(bitacora);

        restBitacoraMockMvc.perform(post("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isBadRequest());

        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJhUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitacoraRepository.findAll().size();
        // set the field null
        bitacora.setJhUserId(null);

        // Create the Bitacora, which fails.
        BitacoraDTO bitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(bitacora);

        restBitacoraMockMvc.perform(post("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isBadRequest());

        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = bitacoraRepository.findAll().size();
        // set the field null
        bitacora.setDescripcion(null);

        // Create the Bitacora, which fails.
        BitacoraDTO bitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(bitacora);

        restBitacoraMockMvc.perform(post("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isBadRequest());

        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBitacoras() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get all the bitacoraList
        restBitacoraMockMvc.perform(get("/api/bitacoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bitacora.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].jhUserId").value(hasItem(DEFAULT_JH_USER_ID)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getBitacora() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);

        // Get the bitacora
        restBitacoraMockMvc.perform(get("/api/bitacoras/{id}", bitacora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bitacora.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.jhUserId").value(DEFAULT_JH_USER_ID))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBitacora() throws Exception {
        // Get the bitacora
        restBitacoraMockMvc.perform(get("/api/bitacoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBitacora() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);
        int databaseSizeBeforeUpdate = bitacoraRepository.findAll().size();

        // Update the bitacora
        Bitacora updatedBitacora = bitacoraRepository.findOne(bitacora.getId());
        updatedBitacora
                .fecha(UPDATED_FECHA)
                .jhUserId(UPDATED_JH_USER_ID)
                .descripcion(UPDATED_DESCRIPCION);
        BitacoraDTO bitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(updatedBitacora);

        restBitacoraMockMvc.perform(put("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isOk());

        // Validate the Bitacora in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeUpdate);
        Bitacora testBitacora = bitacoraList.get(bitacoraList.size() - 1);
        assertThat(testBitacora.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testBitacora.getJhUserId()).isEqualTo(UPDATED_JH_USER_ID);
        assertThat(testBitacora.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingBitacora() throws Exception {
        int databaseSizeBeforeUpdate = bitacoraRepository.findAll().size();

        // Create the Bitacora
        BitacoraDTO bitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(bitacora);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBitacoraMockMvc.perform(put("/api/bitacoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bitacoraDTO)))
            .andExpect(status().isCreated());

        // Validate the Bitacora in the database
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBitacora() throws Exception {
        // Initialize the database
        bitacoraRepository.saveAndFlush(bitacora);
        int databaseSizeBeforeDelete = bitacoraRepository.findAll().size();

        // Get the bitacora
        restBitacoraMockMvc.perform(delete("/api/bitacoras/{id}", bitacora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bitacora> bitacoraList = bitacoraRepository.findAll();
        assertThat(bitacoraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
