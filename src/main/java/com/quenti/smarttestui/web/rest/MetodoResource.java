package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.MetodoService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.MetodoDTO;

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
 * REST controller for managing Metodo.
 */
@RestController
@RequestMapping("/api")
public class MetodoResource {

    private final Logger log = LoggerFactory.getLogger(MetodoResource.class);
        
    @Inject
    private MetodoService metodoService;

    /**
     * POST  /metodos : Create a new metodo.
     *
     * @param metodoDTO the metodoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new metodoDTO, or with status 400 (Bad Request) if the metodo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/metodos")
    @Timed
    public ResponseEntity<MetodoDTO> createMetodo(@Valid @RequestBody MetodoDTO metodoDTO) throws URISyntaxException {
        log.debug("REST request to save Metodo : {}", metodoDTO);
        if (metodoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("metodo", "idexists", "A new metodo cannot already have an ID")).body(null);
        }
        MetodoDTO result = metodoService.save(metodoDTO);
        return ResponseEntity.created(new URI("/api/metodos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("metodo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /metodos : Updates an existing metodo.
     *
     * @param metodoDTO the metodoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated metodoDTO,
     * or with status 400 (Bad Request) if the metodoDTO is not valid,
     * or with status 500 (Internal Server Error) if the metodoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/metodos")
    @Timed
    public ResponseEntity<MetodoDTO> updateMetodo(@Valid @RequestBody MetodoDTO metodoDTO) throws URISyntaxException {
        log.debug("REST request to update Metodo : {}", metodoDTO);
        if (metodoDTO.getId() == null) {
            return createMetodo(metodoDTO);
        }
        MetodoDTO result = metodoService.save(metodoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("metodo", metodoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /metodos : get all the metodos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of metodos in body
     */
    @GetMapping("/metodos")
    @Timed
    public List<MetodoDTO> getAllMetodos() {
        log.debug("REST request to get all Metodos");
        return metodoService.findAll();
    }

    /**
     * GET  /metodos/:id : get the "id" metodo.
     *
     * @param id the id of the metodoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the metodoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/metodos/{id}")
    @Timed
    public ResponseEntity<MetodoDTO> getMetodo(@PathVariable Long id) {
        log.debug("REST request to get Metodo : {}", id);
        MetodoDTO metodoDTO = metodoService.findOne(id);
        return Optional.ofNullable(metodoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /metodos/:id : delete the "id" metodo.
     *
     * @param id the id of the metodoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/metodos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMetodo(@PathVariable Long id) {
        log.debug("REST request to delete Metodo : {}", id);
        metodoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("metodo", id.toString())).build();
    }

}
