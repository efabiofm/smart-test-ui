package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.PlanPruebaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PlanPrueba and its DTO PlanPruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {PruebaMapper.class, })
public interface PlanPruebaMapper {

    PlanPruebaDTO planPruebaToPlanPruebaDTO(PlanPrueba planPrueba);

    List<PlanPruebaDTO> planPruebasToPlanPruebaDTOs(List<PlanPrueba> planPruebas);

    PlanPrueba planPruebaDTOToPlanPrueba(PlanPruebaDTO planPruebaDTO);

    List<PlanPrueba> planPruebaDTOsToPlanPruebas(List<PlanPruebaDTO> planPruebaDTOs);

    default Prueba pruebaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Prueba prueba = new Prueba();
        prueba.setId(id);
        return prueba;
    }
}
