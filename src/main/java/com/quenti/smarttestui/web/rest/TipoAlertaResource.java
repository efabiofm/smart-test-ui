package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.TipoAlertaService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.TipoAlertaDTO;

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
 * REST controller for managing TipoAlerta.
 */
@RestController
@RequestMapping("/api")
public class TipoAlertaResource {

    private final Logger log = LoggerFactory.getLogger(TipoAlertaResource.class);
        
    @Inject
    private TipoAlertaService tipoAlertaService;

    /**
     * POST  /tipo-alertas : Create a new tipoAlerta.
     *
     * @param tipoAlertaDTO the tipoAlertaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoAlertaDTO, or with status 400 (Bad Request) if the tipoAlerta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-alertas")
    @Timed
    public ResponseEntity<TipoAlertaDTO> createTipoAlerta(@Valid @RequestBody TipoAlertaDTO tipoAlertaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoAlerta : {}", tipoAlertaDTO);
        if (tipoAlertaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoAlerta", "idexists", "A new tipoAlerta cannot already have an ID")).body(null);
        }
        TipoAlertaDTO result = tipoAlertaService.save(tipoAlertaDTO);
        return ResponseEntity.created(new URI("/api/tipo-alertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoAlerta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-alertas : Updates an existing tipoAlerta.
     *
     * @param tipoAlertaDTO the tipoAlertaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoAlertaDTO,
     * or with status 400 (Bad Request) if the tipoAlertaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoAlertaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-alertas")
    @Timed
    public ResponseEntity<TipoAlertaDTO> updateTipoAlerta(@Valid @RequestBody TipoAlertaDTO tipoAlertaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoAlerta : {}", tipoAlertaDTO);
        if (tipoAlertaDTO.getId() == null) {
            return createTipoAlerta(tipoAlertaDTO);
        }
        TipoAlertaDTO result = tipoAlertaService.save(tipoAlertaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoAlerta", tipoAlertaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-alertas : get all the tipoAlertas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoAlertas in body
     */
    @GetMapping("/tipo-alertas")
    @Timed
    public List<TipoAlertaDTO> getAllTipoAlertas() {
        log.debug("REST request to get all TipoAlertas");
        return tipoAlertaService.findAll();
    }

    /**
     * GET  /tipo-alertas/:id : get the "id" tipoAlerta.
     *
     * @param id the id of the tipoAlertaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoAlertaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-alertas/{id}")
    @Timed
    public ResponseEntity<TipoAlertaDTO> getTipoAlerta(@PathVariable Long id) {
        log.debug("REST request to get TipoAlerta : {}", id);
        TipoAlertaDTO tipoAlertaDTO = tipoAlertaService.findOne(id);
        return Optional.ofNullable(tipoAlertaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-alertas/:id : delete the "id" tipoAlerta.
     *
     * @param id the id of the tipoAlertaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-alertas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoAlerta(@PathVariable Long id) {
        log.debug("REST request to delete TipoAlerta : {}", id);
        tipoAlertaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoAlerta", id.toString())).build();
    }

}
