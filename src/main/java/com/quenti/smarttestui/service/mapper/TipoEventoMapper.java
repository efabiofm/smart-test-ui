package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.TipoEventoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TipoEvento and its DTO TipoEventoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoEventoMapper {

    TipoEventoDTO tipoEventoToTipoEventoDTO(TipoEvento tipoEvento);

    List<TipoEventoDTO> tipoEventosToTipoEventoDTOs(List<TipoEvento> tipoEventos);

    @Mapping(target = "bitacoras", ignore = true)
    TipoEvento tipoEventoDTOToTipoEvento(TipoEventoDTO tipoEventoDTO);

    List<TipoEvento> tipoEventoDTOsToTipoEventos(List<TipoEventoDTO> tipoEventoDTOs);
}
