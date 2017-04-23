package com.quenti.smarttestui.service;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.repository.PruebaRepository;
import com.quenti.smarttestui.service.dto.PruebaDTO;
import com.quenti.smarttestui.service.dto.PruebaUrlDTO;
import com.quenti.smarttestui.service.mapper.PruebaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Prueba.
 */
@Service
@Transactional
public class PruebaService {

    private final Logger log = LoggerFactory.getLogger(PruebaService.class);

    @Inject
    private PruebaRepository pruebaRepository;

    @Inject
    private PruebaMapper pruebaMapper;

    @Inject
    private ModuloService moduloService;

    /**
     * Save a prueba.
     *
     * @param pruebaDTO the entity to save
     * @return the persisted entity
     */
    public PruebaDTO save(PruebaDTO pruebaDTO) {
        log.debug("Request to save Prueba : {}", pruebaDTO);
        pruebaDTO.setActivo(true);
        Prueba prueba = pruebaMapper.pruebaDTOToPrueba(pruebaDTO);
        prueba = pruebaRepository.save(prueba);
        PruebaDTO result = pruebaMapper.pruebaToPruebaDTO(prueba);
        return result;
    }

    /**
     *  Get all the pruebas.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PruebaDTO> findAll() {
        log.debug("Request to get all Pruebas");
        List<PruebaDTO> result = pruebaRepository.findByActivoTrue().stream()
            .map(pruebaMapper::pruebaToPruebaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one prueba by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PruebaDTO findOne(Long id) {
        log.debug("Request to get Prueba : {}", id);
        Prueba prueba = pruebaRepository.findOne(id);
        PruebaDTO pruebaDTO = pruebaMapper.pruebaToPruebaDTO(prueba);
        return pruebaDTO;
    }

    /**
     *  Delete the  prueba by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Prueba : {}", id);
        PruebaDTO pruebaDTO = findOne(id);
        pruebaDTO.setActivo(false);
        Prueba prueba = pruebaMapper.pruebaDTOToPrueba(pruebaDTO);
        pruebaRepository.save(prueba);

    }

    @Transactional(readOnly =  true)
    public PruebaUrlDTO ObtenerURIPorIdPrueba(Long id){

        Prueba prueba = pruebaRepository.findOne(id);
        Modulo modulo = prueba.getModulo();
        Servicio servicio = prueba.getServicio();
        Metodo metodo = prueba.getMetodo();
        Set<Parametro> parametros = metodo.getParametros();

        /*HashMap<String,String> paramsMap = new HashMap<String,String>();
        for(Parametro param : parametros){
            paramsMap.put(param.getNombre(), param.getValor());
        }*/
        Set<Parametro> paramsTemp = new HashSet<Parametro>();
        for(Parametro param : parametros){
            Parametro nvo = new Parametro();
            nvo.setNombre(param.getNombre());
            nvo.setValor(param.getValor());
            paramsTemp.add(nvo);
        }

        String uri = modulo.getUrl() +"/"+ servicio.getUrl() +"/"+ metodo.getUrl();
        PruebaUrlDTO pruebaUrlDTO = new PruebaUrlDTO();
        pruebaUrlDTO.setUrl(uri);
        pruebaUrlDTO.setParametros(paramsTemp);
        pruebaUrlDTO.setBody(prueba.getBody());
        pruebaUrlDTO.setServiceGroupId(prueba.getServiceGroupId());
        pruebaUrlDTO.setServiceProviderId(prueba.getServiceProviderId());
        return pruebaUrlDTO;
    }
}
