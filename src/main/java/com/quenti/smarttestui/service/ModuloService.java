package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Modulo;
import com.quenti.smarttestui.domain.Servicio;
import com.quenti.smarttestui.repository.ModuloRepository;
import com.quenti.smarttestui.service.dto.ModuloDTO;
import com.quenti.smarttestui.service.dto.ServicioDTO;
import com.quenti.smarttestui.service.mapper.ModuloMapper;
import com.quenti.smarttestui.service.mapper.SeguridadMapper;
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
 * Service Implementation for managing Modulo.
 */
@Service
@Transactional
public class ModuloService {

    private final Logger log = LoggerFactory.getLogger(ModuloService.class);

    @Inject
    private ModuloRepository moduloRepository;

    @Inject
    private ModuloMapper moduloMapper;

    @Inject
    private ServicioMapper servicioMapper;

    /**
     * Save a modulo.
     *
     * @param moduloDTO the entity to save
     * @return the persisted entity
     */
    public ModuloDTO save(ModuloDTO moduloDTO) {
        log.debug("Request to save Modulo : {}", moduloDTO);
        moduloDTO.setActivo(true);
        Modulo modulo = moduloMapper.moduloDTOToModulo(moduloDTO);
        modulo = moduloRepository.save(modulo);
        ModuloDTO result = moduloMapper.moduloToModuloDTO(modulo);
        return result;
    }

    /**
     *  Get all the modulos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ModuloDTO> findAll() {
        log.debug("Request to get all Modulos");
        List<ModuloDTO> result = moduloRepository.findByActivoTrue().stream()
            .map(moduloMapper::moduloToModuloDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one modulo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ModuloDTO findOne(Long id) {
        log.debug("Request to get Modulo : {}", id);
        Modulo modulo = moduloRepository.findOneWithEagerRelationships(id);
        ModuloDTO moduloDTO = moduloMapper.moduloToModuloDTO(modulo);
        return moduloDTO;
    }

    /**
     *  Delete the  modulo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Modulo : {}", id);
        ModuloDTO moduloDTO = findOne(id);
        moduloDTO.setActivo(false);
        Modulo modulo = moduloMapper.moduloDTOToModulo(moduloDTO);
        moduloRepository.save(modulo);
    }

    /**
     *  obtiene todos los servicios por modulo
     *
     *  @param idModulo the id of modulo
     */
    @Transactional(readOnly = true)
    public List<ServicioDTO> obtenerServiciosPorModulo(Long idModulo){
        Modulo modulo = moduloRepository.findOne(idModulo);
        List<ServicioDTO> servicioDTOS = modulo.getServicios().stream()
            .map(servicioMapper::servicioToServicioDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return servicioDTOS;
    }

}
