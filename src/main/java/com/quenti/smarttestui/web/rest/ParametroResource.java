package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.ParametroService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.ParametroDTO;

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
 * REST controller for managing Parametro.
 */
@RestController
@RequestMapping("/api")
public class ParametroResource {

    private final Logger log = LoggerFactory.getLogger(ParametroResource.class);
        
    @Inject
    private ParametroService parametroService;

    /**
     * POST  /parametros : Create a new parametro.
     *
     * @param parametroDTO the parametroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametroDTO, or with status 400 (Bad Request) if the parametro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametros")
    @Timed
    public ResponseEntity<ParametroDTO> createParametro(@Valid @RequestBody ParametroDTO parametroDTO) throws URISyntaxException {
        log.debug("REST request to save Parametro : {}", parametroDTO);
        if (parametroDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("parametro", "idexists", "A new parametro cannot already have an ID")).body(null);
        }
        ParametroDTO result = parametroService.save(parametroDTO);
        return ResponseEntity.created(new URI("/api/parametros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("parametro", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametros : Updates an existing parametro.
     *
     * @param parametroDTO the parametroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametroDTO,
     * or with status 400 (Bad Request) if the parametroDTO is not valid,
     * or with status 500 (Internal Server Error) if the parametroDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametros")
    @Timed
    public ResponseEntity<ParametroDTO> updateParametro(@Valid @RequestBody ParametroDTO parametroDTO) throws URISyntaxException {
        log.debug("REST request to update Parametro : {}", parametroDTO);
        if (parametroDTO.getId() == null) {
            return createParametro(parametroDTO);
        }
        ParametroDTO result = parametroService.save(parametroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("parametro", parametroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametros : get all the parametros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parametros in body
     */
    @GetMapping("/parametros")
    @Timed
    public List<ParametroDTO> getAllParametros() {
        log.debug("REST request to get all Parametros");
        return parametroService.findAll();
    }

    /**
     * GET  /parametros/:id : get the "id" parametro.
     *
     * @param id the id of the parametroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parametros/{id}")
    @Timed
    public ResponseEntity<ParametroDTO> getParametro(@PathVariable Long id) {
        log.debug("REST request to get Parametro : {}", id);
        ParametroDTO parametroDTO = parametroService.findOne(id);
        return Optional.ofNullable(parametroDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /parametros/:id : delete the "id" parametro.
     *
     * @param id the id of the parametroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametros/{id}")
    @Timed
    public ResponseEntity<Void> deleteParametro(@PathVariable Long id) {
        log.debug("REST request to delete Parametro : {}", id);
        parametroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("parametro", id.toString())).build();
    }

}
