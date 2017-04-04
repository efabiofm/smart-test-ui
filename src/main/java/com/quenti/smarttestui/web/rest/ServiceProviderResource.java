package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.ServiceProviderService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.ServiceProviderDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ServiceProvider.
 */
@RestController
@RequestMapping("/api")
public class ServiceProviderResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderResource.class);
        
    @Inject
    private ServiceProviderService serviceProviderService;

    /**
     * POST  /service-providers : Create a new serviceProvider.
     *
     * @param serviceProviderDTO the serviceProviderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceProviderDTO, or with status 400 (Bad Request) if the serviceProvider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProviderDTO> createServiceProvider(@Valid @RequestBody ServiceProviderDTO serviceProviderDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceProvider : {}", serviceProviderDTO);
        if (serviceProviderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serviceProvider", "idexists", "A new serviceProvider cannot already have an ID")).body(null);
        }
        ServiceProviderDTO result = serviceProviderService.save(serviceProviderDTO);
        return ResponseEntity.created(new URI("/api/service-providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("serviceProvider", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-providers : Updates an existing serviceProvider.
     *
     * @param serviceProviderDTO the serviceProviderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceProviderDTO,
     * or with status 400 (Bad Request) if the serviceProviderDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceProviderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProviderDTO> updateServiceProvider(@Valid @RequestBody ServiceProviderDTO serviceProviderDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceProvider : {}", serviceProviderDTO);
        if (serviceProviderDTO.getId() == null) {
            return createServiceProvider(serviceProviderDTO);
        }
        ServiceProviderDTO result = serviceProviderService.save(serviceProviderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("serviceProvider", serviceProviderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-providers : get all the serviceProviders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of serviceProviders in body
     */
    @GetMapping("/service-providers")
    @Timed
    public List<ServiceProviderDTO> getAllServiceProviders() {
        log.debug("REST request to get all ServiceProviders");
        return serviceProviderService.findAll();
    }

    /**
     * GET  /service-providers/:id : get the "id" serviceProvider.
     *
     * @param id the id of the serviceProviderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceProviderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<ServiceProviderDTO> getServiceProvider(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvider : {}", id);
        ServiceProviderDTO serviceProviderDTO = serviceProviderService.findOne(id);
        return Optional.ofNullable(serviceProviderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /service-providers/:id : delete the "id" serviceProvider.
     *
     * @param id the id of the serviceProviderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvider : {}", id);
        serviceProviderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("serviceProvider", id.toString())).build();
    }

}
