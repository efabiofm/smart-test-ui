package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Ambiente;
import com.quenti.smarttestui.repository.AmbienteRepository;
import com.quenti.smarttestui.service.dto.AmbienteDTO;
import com.quenti.smarttestui.service.mapper.AmbienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ambiente.
 */
@Service
@Transactional
public class AmbienteService {

    private final Logger log = LoggerFactory.getLogger(AmbienteService.class);
    
    @Inject
    private AmbienteRepository ambienteRepository;

    @Inject
    private AmbienteMapper ambienteMapper;

    /**
     * Save a ambiente.
     *
     * @param ambienteDTO the entity to save
     * @return the persisted entity
     */
    public AmbienteDTO save(AmbienteDTO ambienteDTO) {
        log.debug("Request to save Ambiente : {}", ambienteDTO);
        Ambiente ambiente = ambienteMapper.ambienteDTOToAmbiente(ambienteDTO);
        ambiente = ambienteRepository.save(ambiente);
        AmbienteDTO result = ambienteMapper.ambienteToAmbienteDTO(ambiente);
        return result;
    }

    /**
     *  Get all the ambientes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AmbienteDTO> findAll() {
        log.debug("Request to get all Ambientes");
        List<AmbienteDTO> result = ambienteRepository.findAllWithEagerRelationships().stream()
            .map(ambienteMapper::ambienteToAmbienteDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one ambiente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AmbienteDTO findOne(Long id) {
        log.debug("Request to get Ambiente : {}", id);
        Ambiente ambiente = ambienteRepository.findOneWithEagerRelationships(id);
        AmbienteDTO ambienteDTO = ambienteMapper.ambienteToAmbienteDTO(ambiente);
        return ambienteDTO;
    }

    /**
     *  Delete the  ambiente by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ambiente : {}", id);
        ambienteRepository.delete(id);
    }
}
