package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Seguridad;
import com.quenti.smarttestui.repository.SeguridadRepository;
import com.quenti.smarttestui.service.dto.SeguridadDTO;
import com.quenti.smarttestui.service.mapper.SeguridadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Seguridad.
 */
@Service
@Transactional
public class SeguridadService {

    private final Logger log = LoggerFactory.getLogger(SeguridadService.class);

    @Inject
    private SeguridadRepository seguridadRepository;

    @Inject
    private SeguridadMapper seguridadMapper;

    /**
     * Save a seguridad.
     *
     * @param seguridadDTO the entity to save
     * @return the persisted entity
     */
    public SeguridadDTO save(SeguridadDTO seguridadDTO) {
        log.debug("Request to save Seguridad : {}", seguridadDTO);
        Seguridad seguridad = seguridadMapper.seguridadDTOToSeguridad(seguridadDTO);
        seguridad = seguridadRepository.save(seguridad);
        SeguridadDTO result = seguridadMapper.seguridadToSeguridadDTO(seguridad);
        return result;
    }

    /**
     *  Get all the seguridads.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SeguridadDTO> findAll() {
        log.debug("Request to get all Seguridads");
        List<SeguridadDTO> result = seguridadRepository.findAll().stream()
            .map(seguridadMapper::seguridadToSeguridadDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one seguridad by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SeguridadDTO findOne(Long id) {
        log.debug("Request to get Seguridad : {}", id);
        Seguridad seguridad = seguridadRepository.findOne(id);
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(seguridad);
        return seguridadDTO;
    }

    /**
     *  Delete the  seguridad by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Seguridad : {}", id);
        seguridadRepository.delete(id);
    }

    /**
     *  finds a seguridadDTO by id of a jhipster user
     *
     *  @param id the id of a jhipsterUserId
     *  @return SeguridadDTO
     */
    @Transactional(readOnly = true)
    public SeguridadDTO findBySeguridadId(Long id){
        log.debug("Request to get Seguridad : {}", id);
        Seguridad seguridad = seguridadRepository.findOneByjhUserId(id);
        SeguridadDTO seguridadDTO = seguridadMapper.seguridadToSeguridadDTO(seguridad);
        return seguridadDTO;
    }
}
