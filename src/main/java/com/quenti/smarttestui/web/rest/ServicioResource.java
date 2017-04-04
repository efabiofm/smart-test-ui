package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.ServicioService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.ServicioDTO;

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
 * REST controller for managing Servicio.
 */
@RestController
@RequestMapping("/api")
public class ServicioResource {

    private final Logger log = LoggerFactory.getLogger(ServicioResource.class);
        
    @Inject
    private ServicioService servicioService;

    /**
     * POST  /servicios : Create a new servicio.
     *
     * @param servicioDTO the servicioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servicioDTO, or with status 400 (Bad Request) if the servicio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servicios")
    @Timed
    public ResponseEntity<ServicioDTO> createServicio(@Valid @RequestBody ServicioDTO servicioDTO) throws URISyntaxException {
        log.debug("REST request to save Servicio : {}", servicioDTO);
        if (servicioDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("servicio", "idexists", "A new servicio cannot already have an ID")).body(null);
        }
        ServicioDTO result = servicioService.save(servicioDTO);
        return ResponseEntity.created(new URI("/api/servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("servicio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicios : Updates an existing servicio.
     *
     * @param servicioDTO the servicioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servicioDTO,
     * or with status 400 (Bad Request) if the servicioDTO is not valid,
     * or with status 500 (Internal Server Error) if the servicioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servicios")
    @Timed
    public ResponseEntity<ServicioDTO> updateServicio(@Valid @RequestBody ServicioDTO servicioDTO) throws URISyntaxException {
        log.debug("REST request to update Servicio : {}", servicioDTO);
        if (servicioDTO.getId() == null) {
            return createServicio(servicioDTO);
        }
        ServicioDTO result = servicioService.save(servicioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("servicio", servicioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicios : get all the servicios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servicios in body
     */
    @GetMapping("/servicios")
    @Timed
    public List<ServicioDTO> getAllServicios() {
        log.debug("REST request to get all Servicios");
        return servicioService.findAll();
    }

    /**
     * GET  /servicios/:id : get the "id" servicio.
     *
     * @param id the id of the servicioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servicioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/servicios/{id}")
    @Timed
    public ResponseEntity<ServicioDTO> getServicio(@PathVariable Long id) {
        log.debug("REST request to get Servicio : {}", id);
        ServicioDTO servicioDTO = servicioService.findOne(id);
        return Optional.ofNullable(servicioDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /servicios/:id : delete the "id" servicio.
     *
     * @param id the id of the servicioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servicios/{id}")
    @Timed
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        log.debug("REST request to delete Servicio : {}", id);
        servicioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("servicio", id.toString())).build();
    }

}
