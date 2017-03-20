package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.ServiceProvider;
import com.quenti.smarttestui.repository.ServiceProviderRepository;
import com.quenti.smarttestui.service.dto.ServiceProviderDTO;
import com.quenti.smarttestui.service.mapper.ServiceProviderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ServiceProvider.
 */
@Service
@Transactional
public class ServiceProviderService {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderService.class);
    
    @Inject
    private ServiceProviderRepository serviceProviderRepository;

    @Inject
    private ServiceProviderMapper serviceProviderMapper;

    /**
     * Save a serviceProvider.
     *
     * @param serviceProviderDTO the entity to save
     * @return the persisted entity
     */
    public ServiceProviderDTO save(ServiceProviderDTO serviceProviderDTO) {
        log.debug("Request to save ServiceProvider : {}", serviceProviderDTO);
        ServiceProvider serviceProvider = serviceProviderMapper.serviceProviderDTOToServiceProvider(serviceProviderDTO);
        serviceProvider = serviceProviderRepository.save(serviceProvider);
        ServiceProviderDTO result = serviceProviderMapper.serviceProviderToServiceProviderDTO(serviceProvider);
        return result;
    }

    /**
     *  Get all the serviceProviders.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ServiceProviderDTO> findAll() {
        log.debug("Request to get all ServiceProviders");
        List<ServiceProviderDTO> result = serviceProviderRepository.findAll().stream()
            .map(serviceProviderMapper::serviceProviderToServiceProviderDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one serviceProvider by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ServiceProviderDTO findOne(Long id) {
        log.debug("Request to get ServiceProvider : {}", id);
        ServiceProvider serviceProvider = serviceProviderRepository.findOne(id);
        ServiceProviderDTO serviceProviderDTO = serviceProviderMapper.serviceProviderToServiceProviderDTO(serviceProvider);
        return serviceProviderDTO;
    }

    /**
     *  Delete the  serviceProvider by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvider : {}", id);
        serviceProviderRepository.delete(id);
    }
}
