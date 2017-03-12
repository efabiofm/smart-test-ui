package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.TipoParametroService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.TipoParametroDTO;

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
 * REST controller for managing TipoParametro.
 */
@RestController
@RequestMapping("/api")
public class TipoParametroResource {

    private final Logger log = LoggerFactory.getLogger(TipoParametroResource.class);
        
    @Inject
    private TipoParametroService tipoParametroService;

    /**
     * POST  /tipo-parametros : Create a new tipoParametro.
     *
     * @param tipoParametroDTO the tipoParametroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoParametroDTO, or with status 400 (Bad Request) if the tipoParametro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-parametros")
    @Timed
    public ResponseEntity<TipoParametroDTO> createTipoParametro(@Valid @RequestBody TipoParametroDTO tipoParametroDTO) throws URISyntaxException {
        log.debug("REST request to save TipoParametro : {}", tipoParametroDTO);
        if (tipoParametroDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoParametro", "idexists", "A new tipoParametro cannot already have an ID")).body(null);
        }
        TipoParametroDTO result = tipoParametroService.save(tipoParametroDTO);
        return ResponseEntity.created(new URI("/api/tipo-parametros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoParametro", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-parametros : Updates an existing tipoParametro.
     *
     * @param tipoParametroDTO the tipoParametroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoParametroDTO,
     * or with status 400 (Bad Request) if the tipoParametroDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoParametroDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-parametros")
    @Timed
    public ResponseEntity<TipoParametroDTO> updateTipoParametro(@Valid @RequestBody TipoParametroDTO tipoParametroDTO) throws URISyntaxException {
        log.debug("REST request to update TipoParametro : {}", tipoParametroDTO);
        if (tipoParametroDTO.getId() == null) {
            return createTipoParametro(tipoParametroDTO);
        }
        TipoParametroDTO result = tipoParametroService.save(tipoParametroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoParametro", tipoParametroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-parametros : get all the tipoParametros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoParametros in body
     */
    @GetMapping("/tipo-parametros")
    @Timed
    public List<TipoParametroDTO> getAllTipoParametros() {
        log.debug("REST request to get all TipoParametros");
        return tipoParametroService.findAll();
    }

    /**
     * GET  /tipo-parametros/:id : get the "id" tipoParametro.
     *
     * @param id the id of the tipoParametroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoParametroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-parametros/{id}")
    @Timed
    public ResponseEntity<TipoParametroDTO> getTipoParametro(@PathVariable Long id) {
        log.debug("REST request to get TipoParametro : {}", id);
        TipoParametroDTO tipoParametroDTO = tipoParametroService.findOne(id);
        return Optional.ofNullable(tipoParametroDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-parametros/:id : delete the "id" tipoParametro.
     *
     * @param id the id of the tipoParametroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-parametros/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoParametro(@PathVariable Long id) {
        log.debug("REST request to delete TipoParametro : {}", id);
        tipoParametroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoParametro", id.toString())).build();
    }

}
