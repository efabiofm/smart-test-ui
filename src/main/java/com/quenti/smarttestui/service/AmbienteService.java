package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Ambiente;
import com.quenti.smarttestui.repository.AmbienteRepository;
import com.quenti.smarttestui.repository.ModuloRepository;
import com.quenti.smarttestui.service.dto.AmbienteDTO;
import com.quenti.smarttestui.service.dto.ModuloDTO;
import com.quenti.smarttestui.service.mapper.AmbienteMapper;
import com.quenti.smarttestui.service.mapper.ModuloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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

    @Inject
    private ModuloMapper moduloMapper;

    /**
     * Save a ambiente.
     *
     * @param ambienteDTO the entity to save
     * @return the persisted entity
     */
    public AmbienteDTO save(AmbienteDTO ambienteDTO) {
        log.debug("Request to save Ambiente : {}", ambienteDTO);
        ambienteDTO.setActivo(true);
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
        List<AmbienteDTO> result = ambienteRepository.findByActivoTrue().stream()
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
        Ambiente ambiente = ambienteRepository.findOne(id);
        AmbienteDTO ambienteDTO = ambienteMapper.ambienteToAmbienteDTO(ambiente);
        return ambienteDTO;
    }

    /**
     *  soft delete the  ambiente by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ambiente : {}", id);
        AmbienteDTO ambienteDTO = findOne(id);
        ambienteDTO.setActivo(false);
        Ambiente ambiente = ambienteMapper.ambienteDTOToAmbiente(ambienteDTO);
        ambienteRepository.save(ambiente);

    }

    /**
     *  obtiene todos los modulos por ambiente
     *
     *  @param idAmbiente the id of ambiente
     */
    @Transactional(readOnly = true)
        public List<ModuloDTO> obtenerModulosPorAmbiente(Long idAmbiente){
        Ambiente ambiente = ambienteRepository.findOne(idAmbiente);
        List<ModuloDTO> moduloDTOS = ambiente.getModulos().stream()
            .map(moduloMapper::moduloToModuloDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return moduloDTOS;
    }

}
