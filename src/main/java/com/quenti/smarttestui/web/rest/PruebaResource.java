package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.PruebaService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.PruebaDTO;

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
 * REST controller for managing Prueba.
 */
@RestController
@RequestMapping("/api")
public class PruebaResource {

    private final Logger log = LoggerFactory.getLogger(PruebaResource.class);
        
    @Inject
    private PruebaService pruebaService;

    /**
     * POST  /pruebas : Create a new prueba.
     *
     * @param pruebaDTO the pruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pruebaDTO, or with status 400 (Bad Request) if the prueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pruebas")
    @Timed
    public ResponseEntity<PruebaDTO> createPrueba(@Valid @RequestBody PruebaDTO pruebaDTO) throws URISyntaxException {
        log.debug("REST request to save Prueba : {}", pruebaDTO);
        if (pruebaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prueba", "idexists", "A new prueba cannot already have an ID")).body(null);
        }
        PruebaDTO result = pruebaService.save(pruebaDTO);
        return ResponseEntity.created(new URI("/api/pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prueba", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pruebas : Updates an existing prueba.
     *
     * @param pruebaDTO the pruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pruebaDTO,
     * or with status 400 (Bad Request) if the pruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the pruebaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pruebas")
    @Timed
    public ResponseEntity<PruebaDTO> updatePrueba(@Valid @RequestBody PruebaDTO pruebaDTO) throws URISyntaxException {
        log.debug("REST request to update Prueba : {}", pruebaDTO);
        if (pruebaDTO.getId() == null) {
            return createPrueba(pruebaDTO);
        }
        PruebaDTO result = pruebaService.save(pruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prueba", pruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pruebas : get all the pruebas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pruebas in body
     */
    @GetMapping("/pruebas")
    @Timed
    public List<PruebaDTO> getAllPruebas() {
        log.debug("REST request to get all Pruebas");
        return pruebaService.findAll();
    }

    /**
     * GET  /pruebas/:id : get the "id" prueba.
     *
     * @param id the id of the pruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<PruebaDTO> getPrueba(@PathVariable Long id) {
        log.debug("REST request to get Prueba : {}", id);
        PruebaDTO pruebaDTO = pruebaService.findOne(id);
        return Optional.ofNullable(pruebaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pruebas/:id : delete the "id" prueba.
     *
     * @param id the id of the pruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deletePrueba(@PathVariable Long id) {
        log.debug("REST request to delete Prueba : {}", id);
        pruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prueba", id.toString())).build();
    }

}
