package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.TipoParametro;
import com.quenti.smarttestui.repository.TipoParametroRepository;
import com.quenti.smarttestui.service.dto.TipoParametroDTO;
import com.quenti.smarttestui.service.mapper.TipoParametroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TipoParametro.
 */
@Service
@Transactional
public class TipoParametroService {

    private final Logger log = LoggerFactory.getLogger(TipoParametroService.class);
    
    @Inject
    private TipoParametroRepository tipoParametroRepository;

    @Inject
    private TipoParametroMapper tipoParametroMapper;

    /**
     * Save a tipoParametro.
     *
     * @param tipoParametroDTO the entity to save
     * @return the persisted entity
     */
    public TipoParametroDTO save(TipoParametroDTO tipoParametroDTO) {
        log.debug("Request to save TipoParametro : {}", tipoParametroDTO);
        TipoParametro tipoParametro = tipoParametroMapper.tipoParametroDTOToTipoParametro(tipoParametroDTO);
        tipoParametro = tipoParametroRepository.save(tipoParametro);
        TipoParametroDTO result = tipoParametroMapper.tipoParametroToTipoParametroDTO(tipoParametro);
        return result;
    }

    /**
     *  Get all the tipoParametros.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TipoParametroDTO> findAll() {
        log.debug("Request to get all TipoParametros");
        List<TipoParametroDTO> result = tipoParametroRepository.findAll().stream()
            .map(tipoParametroMapper::tipoParametroToTipoParametroDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tipoParametro by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TipoParametroDTO findOne(Long id) {
        log.debug("Request to get TipoParametro : {}", id);
        TipoParametro tipoParametro = tipoParametroRepository.findOne(id);
        TipoParametroDTO tipoParametroDTO = tipoParametroMapper.tipoParametroToTipoParametroDTO(tipoParametro);
        return tipoParametroDTO;
    }

    /**
     *  Delete the  tipoParametro by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoParametro : {}", id);
        tipoParametroRepository.delete(id);
    }
}
