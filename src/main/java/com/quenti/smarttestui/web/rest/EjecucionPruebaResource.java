package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.EjecucionPruebaService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.EjecucionPruebaDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing EjecucionPrueba.
 */
@RestController
@RequestMapping("/api")
public class EjecucionPruebaResource {

    private final Logger log = LoggerFactory.getLogger(EjecucionPruebaResource.class);
        
    @Inject
    private EjecucionPruebaService ejecucionPruebaService;

    /**
     * POST  /ejecucion-pruebas : Create a new ejecucionPrueba.
     *
     * @param ejecucionPruebaDTO the ejecucionPruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ejecucionPruebaDTO, or with status 400 (Bad Request) if the ejecucionPrueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ejecucion-pruebas")
    @Timed
    public ResponseEntity<EjecucionPruebaDTO> createEjecucionPrueba(@RequestBody EjecucionPruebaDTO ejecucionPruebaDTO) throws URISyntaxException {
        log.debug("REST request to save EjecucionPrueba : {}", ejecucionPruebaDTO);
        if (ejecucionPruebaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ejecucionPrueba", "idexists", "A new ejecucionPrueba cannot already have an ID")).body(null);
        }
        EjecucionPruebaDTO result = ejecucionPruebaService.save(ejecucionPruebaDTO);
        return ResponseEntity.created(new URI("/api/ejecucion-pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ejecucionPrueba", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ejecucion-pruebas : Updates an existing ejecucionPrueba.
     *
     * @param ejecucionPruebaDTO the ejecucionPruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ejecucionPruebaDTO,
     * or with status 400 (Bad Request) if the ejecucionPruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the ejecucionPruebaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ejecucion-pruebas")
    @Timed
    public ResponseEntity<EjecucionPruebaDTO> updateEjecucionPrueba(@RequestBody EjecucionPruebaDTO ejecucionPruebaDTO) throws URISyntaxException {
        log.debug("REST request to update EjecucionPrueba : {}", ejecucionPruebaDTO);
        if (ejecucionPruebaDTO.getId() == null) {
            return createEjecucionPrueba(ejecucionPruebaDTO);
        }
        EjecucionPruebaDTO result = ejecucionPruebaService.save(ejecucionPruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ejecucionPrueba", ejecucionPruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ejecucion-pruebas : get all the ejecucionPruebas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ejecucionPruebas in body
     */
    @GetMapping("/ejecucion-pruebas")
    @Timed
    public List<EjecucionPruebaDTO> getAllEjecucionPruebas() {
        log.debug("REST request to get all EjecucionPruebas");
        return ejecucionPruebaService.findAll();
    }

    /**
     * GET  /ejecucion-pruebas/:id : get the "id" ejecucionPrueba.
     *
     * @param id the id of the ejecucionPruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ejecucionPruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ejecucion-pruebas/{id}")
    @Timed
    public ResponseEntity<EjecucionPruebaDTO> getEjecucionPrueba(@PathVariable Long id) {
        log.debug("REST request to get EjecucionPrueba : {}", id);
        EjecucionPruebaDTO ejecucionPruebaDTO = ejecucionPruebaService.findOne(id);
        return Optional.ofNullable(ejecucionPruebaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ejecucion-pruebas/:id : delete the "id" ejecucionPrueba.
     *
     * @param id the id of the ejecucionPruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ejecucion-pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEjecucionPrueba(@PathVariable Long id) {
        log.debug("REST request to delete EjecucionPrueba : {}", id);
        ejecucionPruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ejecucionPrueba", id.toString())).build();
    }

}
