package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.domain.Modulo;
import com.quenti.smarttestui.service.AmbienteService;
import com.quenti.smarttestui.service.dto.ModuloDTO;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.AmbienteDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
 * REST controller for managing Ambiente.
 */
@RestController
@RequestMapping("/api")
public class AmbienteResource {

    private final Logger log = LoggerFactory.getLogger(AmbienteResource.class);

    @Inject
    private AmbienteService ambienteService;

    /**
     * POST  /ambientes : Create a new ambiente.
     *
     * @param ambienteDTO the ambienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ambienteDTO, or with status 400 (Bad Request) if the ambiente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ambientes")
    @Timed
    public ResponseEntity<AmbienteDTO> createAmbiente(@Valid @RequestBody AmbienteDTO ambienteDTO) throws URISyntaxException {
        log.debug("REST request to save Ambiente : {}", ambienteDTO);
        if (ambienteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ambiente", "idexists", "A new ambiente cannot already have an ID")).body(null);
        }
        AmbienteDTO result = ambienteService.save(ambienteDTO);
        return ResponseEntity.created(new URI("/api/ambientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ambiente", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ambientes : Updates an existing ambiente.
     *
     * @param ambienteDTO the ambienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ambienteDTO,
     * or with status 400 (Bad Request) if the ambienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the ambienteDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ambientes")
    @Timed
    public ResponseEntity<AmbienteDTO> updateAmbiente(@Valid @RequestBody AmbienteDTO ambienteDTO) throws URISyntaxException {
        log.debug("REST request to update Ambiente : {}", ambienteDTO);
        if (ambienteDTO.getId() == null) {
            return createAmbiente(ambienteDTO);
        }
        AmbienteDTO result = ambienteService.save(ambienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ambiente", ambienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ambientes : get all the ambientes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ambientes in body
     */
    @GetMapping("/ambientes")
    @Timed
    public List<AmbienteDTO> getAllAmbientes() {
        log.debug("REST request to get all Ambientes");
        return ambienteService.findAll();
    }

    /**
     * GET  /ambientes/:id : get the "id" ambiente.
     *
     * @param id the id of the ambienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ambienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ambientes/{id}")
    @Timed
    public ResponseEntity<AmbienteDTO> getAmbiente(@PathVariable Long id) {
        log.debug("REST request to get Ambiente : {}", id);
        AmbienteDTO ambienteDTO = ambienteService.findOne(id);
        return Optional.ofNullable(ambienteDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ambientes/:id : delete the "id" ambiente.
     *
     * @param id the id of the ambienteDTO to soft delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ambientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmbiente(@PathVariable Long id) {
        log.debug("REST request to delete Ambiente : {}", id);
        ambienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ambiente", id.toString())).build();
    }

    /**
     * GET : gets all the ambientes with all the modulos asociated
     *
     * @param id the id of the ambiente
     * @return the list of all the modulos
     */
    @GetMapping("/ambientes/getModules/{id}")
    @Timed
    public List<ModuloDTO> getModulosPorIdAmbiente(@PathVariable Long id){

        return ambienteService.obtenerModulosPorAmbiente(id);

    }


}
