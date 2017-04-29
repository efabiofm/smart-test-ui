package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.TipoEvento;
import com.quenti.smarttestui.repository.TipoEventoRepository;
import com.quenti.smarttestui.service.dto.TipoEventoDTO;
import com.quenti.smarttestui.service.mapper.TipoEventoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TipoEvento.
 */
@Service
@Transactional
public class TipoEventoService {

    private final Logger log = LoggerFactory.getLogger(TipoEventoService.class);

    @Inject
    private TipoEventoRepository tipoEventoRepository;

    @Inject
    private TipoEventoMapper tipoEventoMapper;

    /**
     * Save a tipoEvento.
     *
     * @param tipoEventoDTO the entity to save
     * @return the persisted entity
     */
    public TipoEventoDTO save(TipoEventoDTO tipoEventoDTO) {
        log.debug("Request to save TipoEvento : {}", tipoEventoDTO);
        tipoEventoDTO.setActivo(true);
        TipoEvento tipoEvento = tipoEventoMapper.tipoEventoDTOToTipoEvento(tipoEventoDTO);
        tipoEvento = tipoEventoRepository.save(tipoEvento);
        TipoEventoDTO result = tipoEventoMapper.tipoEventoToTipoEventoDTO(tipoEvento);
        return result;
    }

    /**
     *  Get all the tipoEventos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TipoEventoDTO> findAll() {
        log.debug("Request to get all TipoEventos");
        List<TipoEventoDTO> result = tipoEventoRepository.findByActivoTrue().stream()
            .map(tipoEventoMapper::tipoEventoToTipoEventoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tipoEvento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TipoEventoDTO findOne(Long id) {
        log.debug("Request to get TipoEvento : {}", id);
        TipoEvento tipoEvento = tipoEventoRepository.findOne(id);
        TipoEventoDTO tipoEventoDTO = tipoEventoMapper.tipoEventoToTipoEventoDTO(tipoEvento);
        return tipoEventoDTO;
    }

    /**
     *  Soft delete the  tipoEvento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoEvento : {}", id);
        TipoEventoDTO tipoEventoDTO = findOne(id);
        tipoEventoDTO.setActivo(false);
        TipoEvento tipoEvento = tipoEventoMapper.tipoEventoDTOToTipoEvento(tipoEventoDTO);
        tipoEventoRepository.save(tipoEvento);
    }
}
