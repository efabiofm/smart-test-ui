package com.quenti.smarttestui.service;

import antlr.StringUtils;
import com.quenti.smarttestui.domain.Parametro;
import com.quenti.smarttestui.domain.PlanPrueba;
import com.quenti.smarttestui.domain.User;
import com.quenti.smarttestui.repository.PlanPruebaRepository;
import com.quenti.smarttestui.service.dto.EjecucionPruebaDTO;
import com.quenti.smarttestui.service.dto.PlanPruebaDTO;
import com.quenti.smarttestui.service.dto.PruebaDTO;
import com.quenti.smarttestui.service.dto.PruebaUrlDTO;
import com.quenti.smarttestui.service.mapper.PlanPruebaMapper;
import org.codehaus.groovy.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PlanPrueba.
 */
@Service
@Transactional
public class PlanPruebaService {

    private final Logger log = LoggerFactory.getLogger(PlanPruebaService.class);

    @Inject
    private PlanPruebaRepository planPruebaRepository;

    @Inject
    private PlanPruebaMapper planPruebaMapper;

    @Inject
    private PruebaService pruebaService;

    @Inject
    private UserService userService;

    @Inject EjecucionPruebaService ejecucionPruebaService;

    /**
     * Save a planPrueba.
     *
     * @param planPruebaDTO the entity to save
     * @return the persisted entity
     */
    public PlanPruebaDTO save(PlanPruebaDTO planPruebaDTO) {
        log.debug("Request to save PlanPrueba : {}", planPruebaDTO);
        planPruebaDTO.setActivo(true);
        PlanPrueba planPrueba = planPruebaMapper.planPruebaDTOToPlanPrueba(planPruebaDTO);
        planPrueba = planPruebaRepository.save(planPrueba);
        PlanPruebaDTO result = planPruebaMapper.planPruebaToPlanPruebaDTO(planPrueba);
        return result;
    }

    /**
     *  Get all the planPruebas.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PlanPruebaDTO> findAll() {
        log.debug("Request to get all PlanPruebas");
        List<PlanPruebaDTO> result = planPruebaRepository.findByActivoTrue().stream()
            .map(planPruebaMapper::planPruebaToPlanPruebaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one planPrueba by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PlanPruebaDTO findOne(Long id) {
        log.debug("Request to get PlanPrueba : {}", id);
        PlanPrueba planPrueba = planPruebaRepository.findOneWithEagerRelationships(id);
        PlanPruebaDTO planPruebaDTO = planPruebaMapper.planPruebaToPlanPruebaDTO(planPrueba);
        return planPruebaDTO;
    }

    /**
     *  Delete the  planPrueba by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanPrueba : {}", id);
        PlanPruebaDTO planPruebaDTO = findOne(id);
        planPruebaDTO.setActivo(false);
        PlanPrueba planPrueba = planPruebaMapper.planPruebaDTOToPlanPrueba(planPruebaDTO);
        planPruebaRepository.save(planPrueba);
    }

    @Transactional
    public List<EjecucionPruebaDTO> ejecutarPlanPruebas(Set<PruebaDTO> lstPruebas) throws InterruptedException {
        User user = userService.getUserWithAuthorities();
        List<EjecucionPruebaDTO> listaEjecuciones = new ArrayList<>();
        for (PruebaDTO prueba: lstPruebas) {

            EjecucionPruebaDTO newEjecucion = new EjecucionPruebaDTO();

            newEjecucion.setPruebaId(prueba.getId());
            PruebaUrlDTO pruebaUrlDTO = pruebaService.ObtenerURIPorIdPrueba(prueba.getId());

            Set<Parametro> params = pruebaUrlDTO.getParametros();
            String urlCompleta = pruebaUrlDTO.getUrl();

            if(params != null){
                urlCompleta += "?";
                for(Parametro param : params){
                    urlCompleta += param.getNombre() + "=" + param.getValor() + "&";
                }
                urlCompleta = urlCompleta.substring(0, urlCompleta.length()-1);
            }

            newEjecucion.setUrl(urlCompleta);
            newEjecucion.setBody(pruebaUrlDTO.getBody());
            newEjecucion.setEstado("Pendiente");
            newEjecucion.setJhUserId(user.getId().intValue());
            newEjecucion.setJhUserName(user.getFirstName());
            newEjecucion.setFecha(LocalDate.now());
            newEjecucion.setServiceGroupId(pruebaUrlDTO.getServiceGroupId());
            newEjecucion.setServiceProviderId(pruebaUrlDTO.getServiceProviderId());
            EjecucionPruebaDTO nvaEjecucion = ejecucionPruebaService.save(newEjecucion);
            listaEjecuciones.add(nvaEjecucion);
            //ejecucionPruebaService.ejecutarPrueba(nvaEjecucion);
        }
        return listaEjecuciones;
    }
}
