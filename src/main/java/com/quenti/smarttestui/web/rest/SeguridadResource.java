package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.SeguridadService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.SeguridadDTO;

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
 * REST controller for managing Seguridad.
 */
@RestController
@RequestMapping("/api")
public class SeguridadResource {

    private final Logger log = LoggerFactory.getLogger(SeguridadResource.class);

    @Inject
    private SeguridadService seguridadService;

    /**
     * POST  /seguridads : Create a new seguridad.
     *
     * @param seguridadDTO the seguridadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seguridadDTO, or with status 400 (Bad Request) if the seguridad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seguridads")
    @Timed
    public ResponseEntity<SeguridadDTO> createSeguridad(@Valid @RequestBody SeguridadDTO seguridadDTO) throws URISyntaxException {
        log.debug("REST request to save Seguridad : {}", seguridadDTO);
        if (seguridadDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("seguridad", "idexists", "A new seguridad cannot already have an ID")).body(null);
        }
        SeguridadDTO result = seguridadService.save(seguridadDTO);
        return ResponseEntity.created(new URI("/api/seguridads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("seguridad", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seguridads : Updates an existing seguridad.
     *
     * @param seguridadDTO the seguridadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seguridadDTO,
     * or with status 400 (Bad Request) if the seguridadDTO is not valid,
     * or with status 500 (Internal Server Error) if the seguridadDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seguridads")
    @Timed
    public ResponseEntity<SeguridadDTO> updateSeguridad(@Valid @RequestBody SeguridadDTO seguridadDTO) throws URISyntaxException {
        log.debug("REST request to update Seguridad : {}", seguridadDTO);
        if (seguridadDTO.getId() == null) {
            return createSeguridad(seguridadDTO);
        }
        SeguridadDTO result = seguridadService.save(seguridadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("seguridad", seguridadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seguridads : get all the seguridads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seguridads in body
     */
    @GetMapping("/seguridads")
    @Timed
    public List<SeguridadDTO> getAllSeguridads() {
        log.debug("REST request to get all Seguridads");
        return seguridadService.findAll();
    }

    /**
     * GET  /seguridads/:id : get the "id" seguridad.
     *
     * @param id the id of the seguridadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seguridadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seguridads/{id}")
    @Timed
    public ResponseEntity<SeguridadDTO> getSeguridad(@PathVariable Long id) {
        log.debug("REST request to get Seguridad : {}", id);
        SeguridadDTO seguridadDTO = seguridadService.findOne(id);
        return Optional.ofNullable(seguridadDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/seguridads/user/{id}")
    @Timed
    public ResponseEntity<SeguridadDTO> getSeguridadByUser(@PathVariable Long id) {
        log.debug("REST request to get Seguridad : {}", id);
        SeguridadDTO seguridadDTO = seguridadService.findBySeguridadId(id);
        return Optional.ofNullable(seguridadDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /seguridads/:id : delete the "id" seguridad.
     *
     * @param id the id of the seguridadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seguridads/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeguridad(@PathVariable Long id) {
        log.debug("REST request to delete Seguridad : {}", id);
        seguridadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("seguridad", id.toString())).build();
    }

}
