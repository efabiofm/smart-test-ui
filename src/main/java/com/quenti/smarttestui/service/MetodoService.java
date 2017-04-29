package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Metodo;
import com.quenti.smarttestui.repository.MetodoRepository;
import com.quenti.smarttestui.service.dto.MetodoDTO;
import com.quenti.smarttestui.service.mapper.MetodoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Metodo.
 */
@Service
@Transactional
public class MetodoService {

    private final Logger log = LoggerFactory.getLogger(MetodoService.class);

    @Inject
    private MetodoRepository metodoRepository;

    @Inject
    private MetodoMapper metodoMapper;

    /**
     * Save a metodo.
     *
     * @param metodoDTO the entity to save
     * @return the persisted entity
     */
    public MetodoDTO save(MetodoDTO metodoDTO) {
        log.debug("Request to save Metodo : {}", metodoDTO);
        metodoDTO.setActivo(true);
        Metodo metodo = metodoMapper.metodoDTOToMetodo(metodoDTO);
        metodo = metodoRepository.save(metodo);
        MetodoDTO result = metodoMapper.metodoToMetodoDTO(metodo);
        return result;
    }

    /**
     *  Get all the metodos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MetodoDTO> findAll() {
        log.debug("Request to get all Metodos");
        List<MetodoDTO> result = metodoRepository.findByActivoTrue().stream()
            .map(metodoMapper::metodoToMetodoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one metodo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MetodoDTO findOne(Long id) {
        log.debug("Request to get Metodo : {}", id);
        Metodo metodo = metodoRepository.findOneWithEagerRelationships(id);
        MetodoDTO metodoDTO = metodoMapper.metodoToMetodoDTO(metodo);
        return metodoDTO;
    }

    /**
     *  Soft Delete the  metodo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Metodo : {}", id);
        MetodoDTO metodoDTO = findOne(id);
        metodoDTO.setActivo(false);
        Metodo metodo = metodoMapper.metodoDTOToMetodo(metodoDTO);
        metodoRepository.save(metodo);
    }
}
