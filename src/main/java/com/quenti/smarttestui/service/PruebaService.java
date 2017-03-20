package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Prueba;
import com.quenti.smarttestui.repository.PruebaRepository;
import com.quenti.smarttestui.service.dto.PruebaDTO;
import com.quenti.smarttestui.service.mapper.PruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Prueba.
 */
@Service
@Transactional
public class PruebaService {

    private final Logger log = LoggerFactory.getLogger(PruebaService.class);
    
    @Inject
    private PruebaRepository pruebaRepository;

    @Inject
    private PruebaMapper pruebaMapper;

    /**
     * Save a prueba.
     *
     * @param pruebaDTO the entity to save
     * @return the persisted entity
     */
    public PruebaDTO save(PruebaDTO pruebaDTO) {
        log.debug("Request to save Prueba : {}", pruebaDTO);
        Prueba prueba = pruebaMapper.pruebaDTOToPrueba(pruebaDTO);
        prueba = pruebaRepository.save(prueba);
        PruebaDTO result = pruebaMapper.pruebaToPruebaDTO(prueba);
        return result;
    }

    /**
     *  Get all the pruebas.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PruebaDTO> findAll() {
        log.debug("Request to get all Pruebas");
        List<PruebaDTO> result = pruebaRepository.findAll().stream()
            .map(pruebaMapper::pruebaToPruebaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one prueba by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PruebaDTO findOne(Long id) {
        log.debug("Request to get Prueba : {}", id);
        Prueba prueba = pruebaRepository.findOne(id);
        PruebaDTO pruebaDTO = pruebaMapper.pruebaToPruebaDTO(prueba);
        return pruebaDTO;
    }

    /**
     *  Delete the  prueba by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Prueba : {}", id);
        pruebaRepository.delete(id);
    }
}