package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.TipoHeader;
import com.quenti.smarttestui.repository.TipoHeaderRepository;
import com.quenti.smarttestui.service.dto.TipoHeaderDTO;
import com.quenti.smarttestui.service.mapper.TipoHeaderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TipoHeader.
 */
@Service
@Transactional
public class TipoHeaderService {

    private final Logger log = LoggerFactory.getLogger(TipoHeaderService.class);
    
    @Inject
    private TipoHeaderRepository tipoHeaderRepository;

    @Inject
    private TipoHeaderMapper tipoHeaderMapper;

    /**
     * Save a tipoHeader.
     *
     * @param tipoHeaderDTO the entity to save
     * @return the persisted entity
     */
    public TipoHeaderDTO save(TipoHeaderDTO tipoHeaderDTO) {
        log.debug("Request to save TipoHeader : {}", tipoHeaderDTO);
        TipoHeader tipoHeader = tipoHeaderMapper.tipoHeaderDTOToTipoHeader(tipoHeaderDTO);
        tipoHeader = tipoHeaderRepository.save(tipoHeader);
        TipoHeaderDTO result = tipoHeaderMapper.tipoHeaderToTipoHeaderDTO(tipoHeader);
        return result;
    }

    /**
     *  Get all the tipoHeaders.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TipoHeaderDTO> findAll() {
        log.debug("Request to get all TipoHeaders");
        List<TipoHeaderDTO> result = tipoHeaderRepository.findAll().stream()
            .map(tipoHeaderMapper::tipoHeaderToTipoHeaderDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tipoHeader by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TipoHeaderDTO findOne(Long id) {
        log.debug("Request to get TipoHeader : {}", id);
        TipoHeader tipoHeader = tipoHeaderRepository.findOne(id);
        TipoHeaderDTO tipoHeaderDTO = tipoHeaderMapper.tipoHeaderToTipoHeaderDTO(tipoHeader);
        return tipoHeaderDTO;
    }

    /**
     *  Delete the  tipoHeader by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoHeader : {}", id);
        tipoHeaderRepository.delete(id);
    }
}
