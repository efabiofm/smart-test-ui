package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.EjecucionPruebaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity EjecucionPrueba and its DTO EjecucionPruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EjecucionPruebaMapper {

    @Mapping(source = "prueba.id", target = "pruebaId")
    EjecucionPruebaDTO ejecucionPruebaToEjecucionPruebaDTO(EjecucionPrueba ejecucionPrueba);

    List<EjecucionPruebaDTO> ejecucionPruebasToEjecucionPruebaDTOs(List<EjecucionPrueba> ejecucionPruebas);

    @Mapping(source = "pruebaId", target = "prueba")
    EjecucionPrueba ejecucionPruebaDTOToEjecucionPrueba(EjecucionPruebaDTO ejecucionPruebaDTO);

    List<EjecucionPrueba> ejecucionPruebaDTOsToEjecucionPruebas(List<EjecucionPruebaDTO> ejecucionPruebaDTOs);

    default Prueba pruebaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Prueba prueba = new Prueba();
        prueba.setId(id);
        return prueba;
    }
}
