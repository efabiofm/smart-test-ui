package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.domain.EjecucionPrueba;
import com.quenti.smarttestui.service.EjecucionPruebaService;
import com.quenti.smarttestui.service.PlanPruebaService;
import com.quenti.smarttestui.service.dto.EjecucionPruebaDTO;
import com.quenti.smarttestui.service.dto.PruebaDTO;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.PlanPruebaDTO;

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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing PlanPrueba.
 */
@RestController
@RequestMapping("/api")
public class PlanPruebaResource {

    private final Logger log = LoggerFactory.getLogger(PlanPruebaResource.class);

    @Inject
    private PlanPruebaService planPruebaService;

    @Inject
    private EjecucionPruebaService ejecucionPruebaService;

    /**
     * POST  /plan-pruebas : Create a new planPrueba.
     *
     * @param planPruebaDTO the planPruebaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planPruebaDTO, or with status 400 (Bad Request) if the planPrueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plan-pruebas")
    @Timed
    public ResponseEntity<PlanPruebaDTO> createPlanPrueba(@Valid @RequestBody PlanPruebaDTO planPruebaDTO) throws URISyntaxException {
        log.debug("REST request to save PlanPrueba : {}", planPruebaDTO);
        if (planPruebaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("planPrueba", "idexists", "A new planPrueba cannot already have an ID")).body(null);
        }
        PlanPruebaDTO result = planPruebaService.save(planPruebaDTO);
        return ResponseEntity.created(new URI("/api/plan-pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("planPrueba", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plan-pruebas : Updates an existing planPrueba.
     *
     * @param planPruebaDTO the planPruebaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planPruebaDTO,
     * or with status 400 (Bad Request) if the planPruebaDTO is not valid,
     * or with status 500 (Internal Server Error) if the planPruebaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plan-pruebas")
    @Timed
    public ResponseEntity<PlanPruebaDTO> updatePlanPrueba(@Valid @RequestBody PlanPruebaDTO planPruebaDTO) throws URISyntaxException {
        log.debug("REST request to update PlanPrueba : {}", planPruebaDTO);
        if (planPruebaDTO.getId() == null) {
            return createPlanPrueba(planPruebaDTO);
        }
        PlanPruebaDTO result = planPruebaService.save(planPruebaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("planPrueba", planPruebaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plan-pruebas : get all the planPruebas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planPruebas in body
     */
    @GetMapping("/plan-pruebas")
    @Timed
    public List<PlanPruebaDTO> getAllPlanPruebas() {
        log.debug("REST request to get all PlanPruebas");
        return planPruebaService.findAll();
    }

    /**
     * GET  /plan-pruebas/:id : get the "id" planPrueba.
     *
     * @param id the id of the planPruebaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planPruebaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/plan-pruebas/{id}")
    @Timed
    public ResponseEntity<PlanPruebaDTO> getPlanPrueba(@PathVariable Long id) {
        log.debug("REST request to get PlanPrueba : {}", id);
        PlanPruebaDTO planPruebaDTO = planPruebaService.findOne(id);
        return Optional.ofNullable(planPruebaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /plan-pruebas/:id : delete the "id" planPrueba.
     *
     * @param id the id of the planPruebaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plan-pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanPrueba(@PathVariable Long id) {
        log.debug("REST request to delete PlanPrueba : {}", id);
        planPruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("planPrueba", id.toString())).build();
    }

    @GetMapping("/plan-pruebas/execPlanPrueba/{id}")
    @Timed
    public void ejecutarPlanPrueba (@PathVariable Long id){
        PlanPruebaDTO planPruebaDTO = planPruebaService.findOne(id);
        try {
            List<EjecucionPruebaDTO> listaEjecuciones = planPruebaService.ejecutarPlanPruebas(planPruebaDTO.getPruebas());
            for(EjecucionPruebaDTO ejecucion : listaEjecuciones){
                ejecucionPruebaService.ejecutarPrueba(ejecucion);
            }
        }catch (Exception e){

        }
    }

}
