package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.service.HeaderService;
import com.quenti.smarttestui.web.rest.util.HeaderUtil;
import com.quenti.smarttestui.service.dto.HeaderDTO;

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
 * REST controller for managing Header.
 */
@RestController
@RequestMapping("/api")
public class HeaderResource {

    private final Logger log = LoggerFactory.getLogger(HeaderResource.class);
        
    @Inject
    private HeaderService headerService;

    /**
     * POST  /headers : Create a new header.
     *
     * @param headerDTO the headerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new headerDTO, or with status 400 (Bad Request) if the header has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/headers")
    @Timed
    public ResponseEntity<HeaderDTO> createHeader(@Valid @RequestBody HeaderDTO headerDTO) throws URISyntaxException {
        log.debug("REST request to save Header : {}", headerDTO);
        if (headerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("header", "idexists", "A new header cannot already have an ID")).body(null);
        }
        HeaderDTO result = headerService.save(headerDTO);
        return ResponseEntity.created(new URI("/api/headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("header", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /headers : Updates an existing header.
     *
     * @param headerDTO the headerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated headerDTO,
     * or with status 400 (Bad Request) if the headerDTO is not valid,
     * or with status 500 (Internal Server Error) if the headerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/headers")
    @Timed
    public ResponseEntity<HeaderDTO> updateHeader(@Valid @RequestBody HeaderDTO headerDTO) throws URISyntaxException {
        log.debug("REST request to update Header : {}", headerDTO);
        if (headerDTO.getId() == null) {
            return createHeader(headerDTO);
        }
        HeaderDTO result = headerService.save(headerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("header", headerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /headers : get all the headers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of headers in body
     */
    @GetMapping("/headers")
    @Timed
    public List<HeaderDTO> getAllHeaders() {
        log.debug("REST request to get all Headers");
        return headerService.findAll();
    }

    /**
     * GET  /headers/:id : get the "id" header.
     *
     * @param id the id of the headerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the headerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/headers/{id}")
    @Timed
    public ResponseEntity<HeaderDTO> getHeader(@PathVariable Long id) {
        log.debug("REST request to get Header : {}", id);
        HeaderDTO headerDTO = headerService.findOne(id);
        return Optional.ofNullable(headerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /headers/:id : delete the "id" header.
     *
     * @param id the id of the headerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/headers/{id}")
    @Timed
    public ResponseEntity<Void> deleteHeader(@PathVariable Long id) {
        log.debug("REST request to delete Header : {}", id);
        headerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("header", id.toString())).build();
    }

}
