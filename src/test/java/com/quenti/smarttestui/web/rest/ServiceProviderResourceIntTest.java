package com.quenti.smarttestui.web.rest;

import com.quenti.smarttestui.SmartTestUiApp;

import com.quenti.smarttestui.domain.ServiceProvider;
import com.quenti.smarttestui.repository.ServiceProviderRepository;
import com.quenti.smarttestui.service.ServiceProviderService;
import com.quenti.smarttestui.service.dto.ServiceProviderDTO;
import com.quenti.smarttestui.service.mapper.ServiceProviderMapper;

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
 * Test class for the ServiceProviderResource REST controller.
 *
 * @see ServiceProviderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartTestUiApp.class)
public class ServiceProviderResourceIntTest {

    private static final Integer DEFAULT_SERVICE_PROVIDER_ID = 1;
    private static final Integer UPDATED_SERVICE_PROVIDER_ID = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = true;
    private static final Boolean UPDATED_ACTIVO = true;

    @Inject
    private ServiceProviderRepository serviceProviderRepository;

    @Inject
    private ServiceProviderMapper serviceProviderMapper;

    @Inject
    private ServiceProviderService serviceProviderService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restServiceProviderMockMvc;

    private ServiceProvider serviceProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceProviderResource serviceProviderResource = new ServiceProviderResource();
        ReflectionTestUtils.setField(serviceProviderResource, "serviceProviderService", serviceProviderService);
        this.restServiceProviderMockMvc = MockMvcBuilders.standaloneSetup(serviceProviderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceProvider createEntity(EntityManager em) {
        ServiceProvider serviceProvider = new ServiceProvider()
                .serviceProviderId(DEFAULT_SERVICE_PROVIDER_ID)
                .nombre(DEFAULT_NOMBRE)
                .activo(DEFAULT_ACTIVO);
        return serviceProvider;
    }

    @Before
    public void initTest() {
        serviceProvider = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceProvider() throws Exception {
        int databaseSizeBeforeCreate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider
        ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.serviceProviderToServiceProviderDTO(serviceProvider);

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProviderDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceProvider testServiceProvider = serviceProviderList.get(serviceProviderList.size() - 1);
        assertThat(testServiceProvider.getServiceProviderId()).isEqualTo(DEFAULT_SERVICE_PROVIDER_ID);
        assertThat(testServiceProvider.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testServiceProvider.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createServiceProviderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider with an existing ID
        ServiceProvider existingServiceProvider = new ServiceProvider();
        existingServiceProvider.setId(1L);
        ServiceProviderDTO existingServiceProviderDTO = serviceProviderMapper.serviceProviderToServiceProviderDTO(existingServiceProvider);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingServiceProviderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkServiceProviderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setServiceProviderId(null);

        // Create the ServiceProvider, which fails.
        ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.serviceProviderToServiceProviderDTO(serviceProvider);

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProviderDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setNombre(null);

        // Create the ServiceProvider, which fails.
        ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.serviceProviderToServiceProviderDTO(serviceProvider);

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProviderDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceProviders() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList
        restServiceProviderMockMvc.perform(get("/api/service-providers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceProviderId").value(hasItem(DEFAULT_SERVICE_PROVIDER_ID)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get the serviceProvider
        restServiceProviderMockMvc.perform(get("/api/service-providers/{id}", serviceProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceProvider.getId().intValue()))
            .andExpect(jsonPath("$.serviceProviderId").value(DEFAULT_SERVICE_PROVIDER_ID))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceProvider() throws Exception {
        // Get the serviceProvider
        restServiceProviderMockMvc.perform(get("/api/service-providers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);
        int databaseSizeBeforeUpdate = serviceProviderRepository.findAll().size();

        // Update the serviceProvider
        ServiceProvider updatedServiceProvider = serviceProviderRepository.findOne(serviceProvider.getId());
        updatedServiceProvider
                .serviceProviderId(UPDATED_SERVICE_PROVIDER_ID)
                .nombre(UPDATED_NOMBRE)
                .activo(UPDATED_ACTIVO);
        ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.serviceProviderToServiceProviderDTO(updatedServiceProvider);

        restServiceProviderMockMvc.perform(put("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProviderDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeUpdate);
        ServiceProvider testServiceProvider = serviceProviderList.get(serviceProviderList.size() - 1);
        assertThat(testServiceProvider.getServiceProviderId()).isEqualTo(UPDATED_SERVICE_PROVIDER_ID);
        assertThat(testServiceProvider.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testServiceProvider.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceProvider() throws Exception {
        int databaseSizeBeforeUpdate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider
        ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.serviceProviderToServiceProviderDTO(serviceProvider);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceProviderMockMvc.perform(put("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProviderDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);
        int databaseSizeBeforeDelete = serviceProviderRepository.findAll().size();

        // Get the serviceProvider
        restServiceProviderMockMvc.perform(delete("/api/service-providers/{id}", serviceProvider.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findByActivoTrue();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
