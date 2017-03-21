package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.TipoHeaderService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.TipoHeaderDTO;

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
 * REST controller for managing TipoHeader.
 */
@RestController
@RequestMapping("/api")
public class TipoHeaderResource {

    private final Logger log = LoggerFactory.getLogger(TipoHeaderResource.class);
        
    @Inject
    private TipoHeaderService tipoHeaderService;

    /**
     * POST  /tipo-headers : Create a new tipoHeader.
     *
     * @param tipoHeaderDTO the tipoHeaderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoHeaderDTO, or with status 400 (Bad Request) if the tipoHeader has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-headers")
    @Timed
    public ResponseEntity<TipoHeaderDTO> createTipoHeader(@Valid @RequestBody TipoHeaderDTO tipoHeaderDTO) throws URISyntaxException {
        log.debug("REST request to save TipoHeader : {}", tipoHeaderDTO);
        if (tipoHeaderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoHeader", "idexists", "A new tipoHeader cannot already have an ID")).body(null);
        }
        TipoHeaderDTO result = tipoHeaderService.save(tipoHeaderDTO);
        return ResponseEntity.created(new URI("/api/tipo-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoHeader", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-headers : Updates an existing tipoHeader.
     *
     * @param tipoHeaderDTO the tipoHeaderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoHeaderDTO,
     * or with status 400 (Bad Request) if the tipoHeaderDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoHeaderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-headers")
    @Timed
    public ResponseEntity<TipoHeaderDTO> updateTipoHeader(@Valid @RequestBody TipoHeaderDTO tipoHeaderDTO) throws URISyntaxException {
        log.debug("REST request to update TipoHeader : {}", tipoHeaderDTO);
        if (tipoHeaderDTO.getId() == null) {
            return createTipoHeader(tipoHeaderDTO);
        }
        TipoHeaderDTO result = tipoHeaderService.save(tipoHeaderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoHeader", tipoHeaderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-headers : get all the tipoHeaders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoHeaders in body
     */
    @GetMapping("/tipo-headers")
    @Timed
    public List<TipoHeaderDTO> getAllTipoHeaders() {
        log.debug("REST request to get all TipoHeaders");
        return tipoHeaderService.findAll();
    }

    /**
     * GET  /tipo-headers/:id : get the "id" tipoHeader.
     *
     * @param id the id of the tipoHeaderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoHeaderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-headers/{id}")
    @Timed
    public ResponseEntity<TipoHeaderDTO> getTipoHeader(@PathVariable Long id) {
        log.debug("REST request to get TipoHeader : {}", id);
        TipoHeaderDTO tipoHeaderDTO = tipoHeaderService.findOne(id);
        return Optional.ofNullable(tipoHeaderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-headers/:id : delete the "id" tipoHeader.
     *
     * @param id the id of the tipoHeaderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-headers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoHeader(@PathVariable Long id) {
        log.debug("REST request to delete TipoHeader : {}", id);
        tipoHeaderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoHeader", id.toString())).build();
    }

}
