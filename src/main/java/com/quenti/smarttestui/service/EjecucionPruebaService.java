package com.quenti.smarttestui.service;

import com.quenti.smarttestui.components.RequestComponent;
import com.quenti.smarttestui.domain.EjecucionPrueba;
import com.quenti.smarttestui.repository.EjecucionPruebaRepository;
import com.quenti.smarttestui.service.dto.EjecucionPruebaDTO;
import com.quenti.smarttestui.service.mapper.EjecucionPruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EjecucionPrueba.
 */
@Service
@Transactional
public class EjecucionPruebaService {

    private final Logger log = LoggerFactory.getLogger(EjecucionPruebaService.class);

    @Inject
    private EjecucionPruebaRepository ejecucionPruebaRepository;

    @Inject
    private EjecucionPruebaMapper ejecucionPruebaMapper;

    @Inject
    private RequestComponent requestComponent;

    /**
     * Save a ejecucionPrueba.
     *
     * @param ejecucionPruebaDTO the entity to save
     * @return the persisted entity
     */
    public EjecucionPruebaDTO save(EjecucionPruebaDTO ejecucionPruebaDTO) {
        log.debug("Request to save EjecucionPrueba : {}", ejecucionPruebaDTO);
        ejecucionPruebaDTO.setActivo(true);
        EjecucionPrueba ejecucionPrueba = ejecucionPruebaMapper.ejecucionPruebaDTOToEjecucionPrueba(ejecucionPruebaDTO);
        ejecucionPrueba = ejecucionPruebaRepository.save(ejecucionPrueba);
        EjecucionPruebaDTO result = ejecucionPruebaMapper.ejecucionPruebaToEjecucionPruebaDTO(ejecucionPrueba);
        return result;
    }

    /**
     *  Get all the ejecucionPruebas.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EjecucionPruebaDTO> findAll() {
        log.debug("Request to get all EjecucionPruebas");
        List<EjecucionPruebaDTO> result = ejecucionPruebaRepository.findByActivoTrue().stream()
            .map(ejecucionPruebaMapper::ejecucionPruebaToEjecucionPruebaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one ejecucionPrueba by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EjecucionPruebaDTO findOne(Long id) {
        log.debug("Request to get EjecucionPrueba : {}", id);
        EjecucionPrueba ejecucionPrueba = ejecucionPruebaRepository.findOne(id);
        EjecucionPruebaDTO ejecucionPruebaDTO = ejecucionPruebaMapper.ejecucionPruebaToEjecucionPruebaDTO(ejecucionPrueba);
        return ejecucionPruebaDTO;
    }

    /**
     *  Delete the  ejecucionPrueba by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EjecucionPrueba : {}", id);
        EjecucionPruebaDTO ejecucionPruebaDTO = findOne(id);
        ejecucionPruebaDTO.setActivo(false);
        EjecucionPrueba ejecucionPrueba = ejecucionPruebaMapper.ejecucionPruebaDTOToEjecucionPrueba(ejecucionPruebaDTO);
        ejecucionPruebaRepository.save(ejecucionPrueba);
    }
    @Transactional
    public String ejecutarPrueba(EjecucionPruebaDTO ejecucionPruebaDTO){
        return requestComponent.init(ejecucionPruebaDTO);
    }
}
