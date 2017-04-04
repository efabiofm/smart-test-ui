package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.TipoAlerta;
import com.quenti.smarttestui.repository.TipoAlertaRepository;
import com.quenti.smarttestui.service.dto.TipoAlertaDTO;
import com.quenti.smarttestui.service.mapper.TipoAlertaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TipoAlerta.
 */
@Service
@Transactional
public class TipoAlertaService {

    private final Logger log = LoggerFactory.getLogger(TipoAlertaService.class);

    @Inject
    private TipoAlertaRepository tipoAlertaRepository;

    @Inject
    private TipoAlertaMapper tipoAlertaMapper;

    /**
     * Save a tipoAlerta.
     *
     * @param tipoAlertaDTO the entity to save
     * @return the persisted entity
     */
    public TipoAlertaDTO save(TipoAlertaDTO tipoAlertaDTO) {
        log.debug("Request to save TipoAlerta : {}", tipoAlertaDTO);
        tipoAlertaDTO.setActivo(true);
        TipoAlerta tipoAlerta = tipoAlertaMapper.tipoAlertaDTOToTipoAlerta(tipoAlertaDTO);
        tipoAlerta = tipoAlertaRepository.save(tipoAlerta);
        TipoAlertaDTO result = tipoAlertaMapper.tipoAlertaToTipoAlertaDTO(tipoAlerta);
        return result;
    }

    /**
     *  Get all the tipoAlertas.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TipoAlertaDTO> findAll() {
        log.debug("Request to get all TipoAlertas");
        List<TipoAlertaDTO> result = tipoAlertaRepository.findByActivoTrue().stream()
            .map(tipoAlertaMapper::tipoAlertaToTipoAlertaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tipoAlerta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TipoAlertaDTO findOne(Long id) {
        log.debug("Request to get TipoAlerta : {}", id);
        TipoAlerta tipoAlerta = tipoAlertaRepository.findOne(id);
        TipoAlertaDTO tipoAlertaDTO = tipoAlertaMapper.tipoAlertaToTipoAlertaDTO(tipoAlerta);
        return tipoAlertaDTO;
    }

    /**
     *  Delete the  tipoAlerta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoAlerta : {}", id);
        TipoAlertaDTO tipoAlertaDTO = findOne(id);
        tipoAlertaDTO.setActivo(false);
        TipoAlerta tipoAlerta = tipoAlertaMapper.tipoAlertaDTOToTipoAlerta(tipoAlertaDTO);
        tipoAlertaRepository.save(tipoAlerta);
    }
}
