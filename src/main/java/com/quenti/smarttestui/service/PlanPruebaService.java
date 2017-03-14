package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.PlanPrueba;
import com.quenti.smarttestui.repository.PlanPruebaRepository;
import com.quenti.smarttestui.service.dto.PlanPruebaDTO;
import com.quenti.smarttestui.service.mapper.PlanPruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PlanPrueba.
 */
@Service
@Transactional
public class PlanPruebaService {

    private final Logger log = LoggerFactory.getLogger(PlanPruebaService.class);
    
    @Inject
    private PlanPruebaRepository planPruebaRepository;

    @Inject
    private PlanPruebaMapper planPruebaMapper;

    /**
     * Save a planPrueba.
     *
     * @param planPruebaDTO the entity to save
     * @return the persisted entity
     */
    public PlanPruebaDTO save(PlanPruebaDTO planPruebaDTO) {
        log.debug("Request to save PlanPrueba : {}", planPruebaDTO);
        PlanPrueba planPrueba = planPruebaMapper.planPruebaDTOToPlanPrueba(planPruebaDTO);
        planPrueba = planPruebaRepository.save(planPrueba);
        PlanPruebaDTO result = planPruebaMapper.planPruebaToPlanPruebaDTO(planPrueba);
        return result;
    }

    /**
     *  Get all the planPruebas.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PlanPruebaDTO> findAll() {
        log.debug("Request to get all PlanPruebas");
        List<PlanPruebaDTO> result = planPruebaRepository.findAllWithEagerRelationships().stream()
            .map(planPruebaMapper::planPruebaToPlanPruebaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one planPrueba by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PlanPruebaDTO findOne(Long id) {
        log.debug("Request to get PlanPrueba : {}", id);
        PlanPrueba planPrueba = planPruebaRepository.findOneWithEagerRelationships(id);
        PlanPruebaDTO planPruebaDTO = planPruebaMapper.planPruebaToPlanPruebaDTO(planPrueba);
        return planPruebaDTO;
    }

    /**
     *  Delete the  planPrueba by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanPrueba : {}", id);
        planPruebaRepository.delete(id);
    }
}
