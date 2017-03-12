package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.ModuloService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.ModuloDTO;

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
 * REST controller for managing Modulo.
 */
@RestController
@RequestMapping("/api")
public class ModuloResource {

    private final Logger log = LoggerFactory.getLogger(ModuloResource.class);
        
    @Inject
    private ModuloService moduloService;

    /**
     * POST  /modulos : Create a new modulo.
     *
     * @param moduloDTO the moduloDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moduloDTO, or with status 400 (Bad Request) if the modulo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modulos")
    @Timed
    public ResponseEntity<ModuloDTO> createModulo(@Valid @RequestBody ModuloDTO moduloDTO) throws URISyntaxException {
        log.debug("REST request to save Modulo : {}", moduloDTO);
        if (moduloDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("modulo", "idexists", "A new modulo cannot already have an ID")).body(null);
        }
        ModuloDTO result = moduloService.save(moduloDTO);
        return ResponseEntity.created(new URI("/api/modulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("modulo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modulos : Updates an existing modulo.
     *
     * @param moduloDTO the moduloDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moduloDTO,
     * or with status 400 (Bad Request) if the moduloDTO is not valid,
     * or with status 500 (Internal Server Error) if the moduloDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modulos")
    @Timed
    public ResponseEntity<ModuloDTO> updateModulo(@Valid @RequestBody ModuloDTO moduloDTO) throws URISyntaxException {
        log.debug("REST request to update Modulo : {}", moduloDTO);
        if (moduloDTO.getId() == null) {
            return createModulo(moduloDTO);
        }
        ModuloDTO result = moduloService.save(moduloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("modulo", moduloDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modulos : get all the modulos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modulos in body
     */
    @GetMapping("/modulos")
    @Timed
    public List<ModuloDTO> getAllModulos() {
        log.debug("REST request to get all Modulos");
        return moduloService.findAll();
    }

    /**
     * GET  /modulos/:id : get the "id" modulo.
     *
     * @param id the id of the moduloDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moduloDTO, or with status 404 (Not Found)
     */
    @GetMapping("/modulos/{id}")
    @Timed
    public ResponseEntity<ModuloDTO> getModulo(@PathVariable Long id) {
        log.debug("REST request to get Modulo : {}", id);
        ModuloDTO moduloDTO = moduloService.findOne(id);
        return Optional.ofNullable(moduloDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /modulos/:id : delete the "id" modulo.
     *
     * @param id the id of the moduloDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modulos/{id}")
    @Timed
    public ResponseEntity<Void> deleteModulo(@PathVariable Long id) {
        log.debug("REST request to delete Modulo : {}", id);
        moduloService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("modulo", id.toString())).build();
    }

}
