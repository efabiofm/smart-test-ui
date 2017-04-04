package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.Alerta;
import com.quenti.smarttestui.repository.AlertaRepository;
import com.quenti.smarttestui.service.dto.AlertaDTO;
import com.quenti.smarttestui.service.mapper.AlertaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Alerta.
 */
@Service
@Transactional
public class AlertaService {

    private final Logger log = LoggerFactory.getLogger(AlertaService.class);

    @Inject
    private AlertaRepository alertaRepository;

    @Inject
    private AlertaMapper alertaMapper;

    /**
     * Save a alerta.
     *
     * @param alertaDTO the entity to save
     * @return the persisted entity
     */
    public AlertaDTO save(AlertaDTO alertaDTO) {
        log.debug("Request to save Alerta : {}", alertaDTO);
        alertaDTO.setActivo(true);
        Alerta alerta = alertaMapper.alertaDTOToAlerta(alertaDTO);
        alerta = alertaRepository.save(alerta);
        AlertaDTO result = alertaMapper.alertaToAlertaDTO(alerta);
        return result;
    }

    /**
     *  Get all the alertas.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlertaDTO> findAll() {
        log.debug("Request to get all Alertas");
        List<AlertaDTO> result = alertaRepository.findByActivoTrue().stream()
            .map(alertaMapper::alertaToAlertaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one alerta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AlertaDTO findOne(Long id) {
        log.debug("Request to get Alerta : {}", id);
        Alerta alerta = alertaRepository.findOne(id);
        AlertaDTO alertaDTO = alertaMapper.alertaToAlertaDTO(alerta);
        return alertaDTO;
    }

    /**
     *  Delete the  alerta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Alerta : {}", id);
//        alertaRepository.delete(id);
        AlertaDTO alertaDTO = findOne(id);
        alertaDTO.setActivo(false);
        Alerta alerta = alertaMapper.alertaDTOToAlerta(alertaDTO);
        alertaRepository.save(alerta);
    }
}
