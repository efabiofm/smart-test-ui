package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.SeguridadDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Seguridad and its DTO SeguridadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeguridadMapper {

    SeguridadDTO seguridadToSeguridadDTO(Seguridad seguridad);

    List<SeguridadDTO> seguridadsToSeguridadDTOs(List<Seguridad> seguridads);

    Seguridad seguridadDTOToSeguridad(SeguridadDTO seguridadDTO);

    List<Seguridad> seguridadDTOsToSeguridads(List<SeguridadDTO> seguridadDTOs);
}
