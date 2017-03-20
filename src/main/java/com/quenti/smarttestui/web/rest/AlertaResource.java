package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.AlertaService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.AlertaDTO;

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
 * REST controller for managing Alerta.
 */
@RestController
@RequestMapping("/api")
public class AlertaResource {

    private final Logger log = LoggerFactory.getLogger(AlertaResource.class);
        
    @Inject
    private AlertaService alertaService;

    /**
     * POST  /alertas : Create a new alerta.
     *
     * @param alertaDTO the alertaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alertaDTO, or with status 400 (Bad Request) if the alerta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alertas")
    @Timed
    public ResponseEntity<AlertaDTO> createAlerta(@Valid @RequestBody AlertaDTO alertaDTO) throws URISyntaxException {
        log.debug("REST request to save Alerta : {}", alertaDTO);
        if (alertaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alerta", "idexists", "A new alerta cannot already have an ID")).body(null);
        }
        AlertaDTO result = alertaService.save(alertaDTO);
        return ResponseEntity.created(new URI("/api/alertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alerta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alertas : Updates an existing alerta.
     *
     * @param alertaDTO the alertaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alertaDTO,
     * or with status 400 (Bad Request) if the alertaDTO is not valid,
     * or with status 500 (Internal Server Error) if the alertaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alertas")
    @Timed
    public ResponseEntity<AlertaDTO> updateAlerta(@Valid @RequestBody AlertaDTO alertaDTO) throws URISyntaxException {
        log.debug("REST request to update Alerta : {}", alertaDTO);
        if (alertaDTO.getId() == null) {
            return createAlerta(alertaDTO);
        }
        AlertaDTO result = alertaService.save(alertaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alerta", alertaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alertas : get all the alertas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alertas in body
     */
    @GetMapping("/alertas")
    @Timed
    public List<AlertaDTO> getAllAlertas() {
        log.debug("REST request to get all Alertas");
        return alertaService.findAll();
    }

    /**
     * GET  /alertas/:id : get the "id" alerta.
     *
     * @param id the id of the alertaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alertaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alertas/{id}")
    @Timed
    public ResponseEntity<AlertaDTO> getAlerta(@PathVariable Long id) {
        log.debug("REST request to get Alerta : {}", id);
        AlertaDTO alertaDTO = alertaService.findOne(id);
        return Optional.ofNullable(alertaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alertas/:id : delete the "id" alerta.
     *
     * @param id the id of the alertaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alertas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlerta(@PathVariable Long id) {
        log.debug("REST request to delete Alerta : {}", id);
        alertaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alerta", id.toString())).build();
    }

}
