package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Servicio;
import com.quenti.smarttestui.repository.ServicioRepository;
import com.quenti.smarttestui.service.dto.ServicioDTO;
import com.quenti.smarttestui.service.mapper.ServicioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Servicio.
 */
@Service
@Transactional
public class ServicioService {

    private final Logger log = LoggerFactory.getLogger(ServicioService.class);

    @Inject
    private ServicioRepository servicioRepository;

    @Inject
    private ServicioMapper servicioMapper;

    /**
     * Save a servicio.
     *
     * @param servicioDTO the entity to save
     * @return the persisted entity
     */
    public ServicioDTO save(ServicioDTO servicioDTO) {
        log.debug("Request to save Servicio : {}", servicioDTO);
        Servicio servicio = servicioMapper.servicioDTOToServicio(servicioDTO);
        servicio = servicioRepository.save(servicio);
        ServicioDTO result = servicioMapper.servicioToServicioDTO(servicio);
        return result;
    }

    /**
     *  Get all the servicios.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ServicioDTO> findAll() {
        log.debug("Request to get all Servicios");
        List<ServicioDTO> result = servicioRepository.findByActivoTrue().stream()
            .map(servicioMapper::servicioToServicioDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one servicio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ServicioDTO findOne(Long id) {
        log.debug("Request to get Servicio : {}", id);
        Servicio servicio = servicioRepository.findOneWithEagerRelationships(id);
        ServicioDTO servicioDTO = servicioMapper.servicioToServicioDTO(servicio);
        return servicioDTO;
    }

    /**
     *  Delete the  servicio by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Servicio : {}", id);
        servicioRepository.delete(id);
    }
}
