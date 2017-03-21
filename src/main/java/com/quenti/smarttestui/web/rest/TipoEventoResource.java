package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.TipoEventoService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.TipoEventoDTO;

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
 * REST controller for managing TipoEvento.
 */
@RestController
@RequestMapping("/api")
public class TipoEventoResource {

    private final Logger log = LoggerFactory.getLogger(TipoEventoResource.class);
        
    @Inject
    private TipoEventoService tipoEventoService;

    /**
     * POST  /tipo-eventos : Create a new tipoEvento.
     *
     * @param tipoEventoDTO the tipoEventoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoEventoDTO, or with status 400 (Bad Request) if the tipoEvento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-eventos")
    @Timed
    public ResponseEntity<TipoEventoDTO> createTipoEvento(@Valid @RequestBody TipoEventoDTO tipoEventoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoEvento : {}", tipoEventoDTO);
        if (tipoEventoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoEvento", "idexists", "A new tipoEvento cannot already have an ID")).body(null);
        }
        TipoEventoDTO result = tipoEventoService.save(tipoEventoDTO);
        return ResponseEntity.created(new URI("/api/tipo-eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoEvento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-eventos : Updates an existing tipoEvento.
     *
     * @param tipoEventoDTO the tipoEventoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoEventoDTO,
     * or with status 400 (Bad Request) if the tipoEventoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoEventoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-eventos")
    @Timed
    public ResponseEntity<TipoEventoDTO> updateTipoEvento(@Valid @RequestBody TipoEventoDTO tipoEventoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoEvento : {}", tipoEventoDTO);
        if (tipoEventoDTO.getId() == null) {
            return createTipoEvento(tipoEventoDTO);
        }
        TipoEventoDTO result = tipoEventoService.save(tipoEventoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoEvento", tipoEventoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-eventos : get all the tipoEventos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoEventos in body
     */
    @GetMapping("/tipo-eventos")
    @Timed
    public List<TipoEventoDTO> getAllTipoEventos() {
        log.debug("REST request to get all TipoEventos");
        return tipoEventoService.findAll();
    }

    /**
     * GET  /tipo-eventos/:id : get the "id" tipoEvento.
     *
     * @param id the id of the tipoEventoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoEventoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-eventos/{id}")
    @Timed
    public ResponseEntity<TipoEventoDTO> getTipoEvento(@PathVariable Long id) {
        log.debug("REST request to get TipoEvento : {}", id);
        TipoEventoDTO tipoEventoDTO = tipoEventoService.findOne(id);
        return Optional.ofNullable(tipoEventoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-eventos/:id : delete the "id" tipoEvento.
     *
     * @param id the id of the tipoEventoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-eventos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoEvento(@PathVariable Long id) {
        log.debug("REST request to delete TipoEvento : {}", id);
        tipoEventoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoEvento", id.toString())).build();
    }

}
