package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.AmbienteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Ambiente and its DTO AmbienteDTO.
 */
@Mapper(componentModel = "spring", uses = {ModuloMapper.class, })
public interface AmbienteMapper {

    AmbienteDTO ambienteToAmbienteDTO(Ambiente ambiente);

    List<AmbienteDTO> ambientesToAmbienteDTOs(List<Ambiente> ambientes);

    @Mapping(target = "pruebas", ignore = true)
    Ambiente ambienteDTOToAmbiente(AmbienteDTO ambienteDTO);

    List<Ambiente> ambienteDTOsToAmbientes(List<AmbienteDTO> ambienteDTOs);

    default Modulo moduloFromId(Long id) {
        if (id == null) {
            return null;
        }
        Modulo modulo = new Modulo();
        modulo.setId(id);
        return modulo;
    }
}
