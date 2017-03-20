package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.AmbienteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Ambiente and its DTO AmbienteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmbienteMapper {

    AmbienteDTO ambienteToAmbienteDTO(Ambiente ambiente);

    List<AmbienteDTO> ambientesToAmbienteDTOs(List<Ambiente> ambientes);

    @Mapping(target = "pruebas", ignore = true)
    @Mapping(target = "modulos", ignore = true)
    Ambiente ambienteDTOToAmbiente(AmbienteDTO ambienteDTO);

    List<Ambiente> ambienteDTOsToAmbientes(List<AmbienteDTO> ambienteDTOs);
}
