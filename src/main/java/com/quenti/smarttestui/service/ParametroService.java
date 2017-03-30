package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Parametro;
import com.quenti.smarttestui.repository.ParametroRepository;
import com.quenti.smarttestui.service.dto.ParametroDTO;
import com.quenti.smarttestui.service.mapper.ParametroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Parametro.
 */
@Service
@Transactional
public class ParametroService {

    private final Logger log = LoggerFactory.getLogger(ParametroService.class);

    @Inject
    private ParametroRepository parametroRepository;

    @Inject
    private ParametroMapper parametroMapper;

    /**
     * Save a parametro.
     *
     * @param parametroDTO the entity to save
     * @return the persisted entity
     */
    public ParametroDTO save(ParametroDTO parametroDTO) {
        log.debug("Request to save Parametro : {}", parametroDTO);
        parametroDTO.setActivo(true);
        Parametro parametro = parametroMapper.parametroDTOToParametro(parametroDTO);
        parametro = parametroRepository.save(parametro);
        ParametroDTO result = parametroMapper.parametroToParametroDTO(parametro);
        return result;
    }

    /**
     *  Get all the parametros.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ParametroDTO> findAll() {
        log.debug("Request to get all Parametros");
        List<ParametroDTO> result = parametroRepository.findByActivoTrue().stream()
            .map(parametroMapper::parametroToParametroDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one parametro by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ParametroDTO findOne(Long id) {
        log.debug("Request to get Parametro : {}", id);
        Parametro parametro = parametroRepository.findOneWithEagerRelationships(id);
        ParametroDTO parametroDTO = parametroMapper.parametroToParametroDTO(parametro);
        return parametroDTO;
    }

    /**
     *  Delete the  parametro by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Parametro : {}", id);
        ParametroDTO parametroDTO = findOne(id);
        parametroDTO.setActivo(false);
        Parametro parametro = parametroMapper.parametroDTOToParametro(parametroDTO);
        parametroRepository.save(parametro);
    }
}
