package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Bitacora;
import com.quenti.smarttestui.repository.BitacoraRepository;
import com.quenti.smarttestui.service.dto.BitacoraDTO;
import com.quenti.smarttestui.service.mapper.BitacoraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Bitacora.
 */
@Service
@Transactional
public class BitacoraService {

    private final Logger log = LoggerFactory.getLogger(BitacoraService.class);
    
    @Inject
    private BitacoraRepository bitacoraRepository;

    @Inject
    private BitacoraMapper bitacoraMapper;

    /**
     * Save a bitacora.
     *
     * @param bitacoraDTO the entity to save
     * @return the persisted entity
     */
    public BitacoraDTO save(BitacoraDTO bitacoraDTO) {
        log.debug("Request to save Bitacora : {}", bitacoraDTO);
        Bitacora bitacora = bitacoraMapper.bitacoraDTOToBitacora(bitacoraDTO);
        bitacora = bitacoraRepository.save(bitacora);
        BitacoraDTO result = bitacoraMapper.bitacoraToBitacoraDTO(bitacora);
        return result;
    }

    /**
     *  Get all the bitacoras.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<BitacoraDTO> findAll() {
        log.debug("Request to get all Bitacoras");
        List<BitacoraDTO> result = bitacoraRepository.findAll().stream()
            .map(bitacoraMapper::bitacoraToBitacoraDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one bitacora by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public BitacoraDTO findOne(Long id) {
        log.debug("Request to get Bitacora : {}", id);
        Bitacora bitacora = bitacoraRepository.findOne(id);
        BitacoraDTO bitacoraDTO = bitacoraMapper.bitacoraToBitacoraDTO(bitacora);
        return bitacoraDTO;
    }

    /**
     *  Delete the  bitacora by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bitacora : {}", id);
        bitacoraRepository.delete(id);
    }
}
