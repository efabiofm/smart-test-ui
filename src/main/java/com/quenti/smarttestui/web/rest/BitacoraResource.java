package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.BitacoraService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.BitacoraDTO;

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
 * REST controller for managing Bitacora.
 */
@RestController
@RequestMapping("/api")
public class BitacoraResource {

    private final Logger log = LoggerFactory.getLogger(BitacoraResource.class);
        
    @Inject
    private BitacoraService bitacoraService;

    /**
     * POST  /bitacoras : Create a new bitacora.
     *
     * @param bitacoraDTO the bitacoraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bitacoraDTO, or with status 400 (Bad Request) if the bitacora has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bitacoras")
    @Timed
    public ResponseEntity<BitacoraDTO> createBitacora(@Valid @RequestBody BitacoraDTO bitacoraDTO) throws URISyntaxException {
        log.debug("REST request to save Bitacora : {}", bitacoraDTO);
        if (bitacoraDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bitacora", "idexists", "A new bitacora cannot already have an ID")).body(null);
        }
        BitacoraDTO result = bitacoraService.save(bitacoraDTO);
        return ResponseEntity.created(new URI("/api/bitacoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bitacora", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bitacoras : Updates an existing bitacora.
     *
     * @param bitacoraDTO the bitacoraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bitacoraDTO,
     * or with status 400 (Bad Request) if the bitacoraDTO is not valid,
     * or with status 500 (Internal Server Error) if the bitacoraDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bitacoras")
    @Timed
    public ResponseEntity<BitacoraDTO> updateBitacora(@Valid @RequestBody BitacoraDTO bitacoraDTO) throws URISyntaxException {
        log.debug("REST request to update Bitacora : {}", bitacoraDTO);
        if (bitacoraDTO.getId() == null) {
            return createBitacora(bitacoraDTO);
        }
        BitacoraDTO result = bitacoraService.save(bitacoraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bitacora", bitacoraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bitacoras : get all the bitacoras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bitacoras in body
     */
    @GetMapping("/bitacoras")
    @Timed
    public List<BitacoraDTO> getAllBitacoras() {
        log.debug("REST request to get all Bitacoras");
        return bitacoraService.findAll();
    }

    /**
     * GET  /bitacoras/:id : get the "id" bitacora.
     *
     * @param id the id of the bitacoraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bitacoraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bitacoras/{id}")
    @Timed
    public ResponseEntity<BitacoraDTO> getBitacora(@PathVariable Long id) {
        log.debug("REST request to get Bitacora : {}", id);
        BitacoraDTO bitacoraDTO = bitacoraService.findOne(id);
        return Optional.ofNullable(bitacoraDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bitacoras/:id : delete the "id" bitacora.
     *
     * @param id the id of the bitacoraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bitacoras/{id}")
    @Timed
    public ResponseEntity<Void> deleteBitacora(@PathVariable Long id) {
        log.debug("REST request to delete Bitacora : {}", id);
        bitacoraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bitacora", id.toString())).build();
    }

}
