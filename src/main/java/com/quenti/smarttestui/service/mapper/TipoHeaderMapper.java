package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.TipoHeaderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TipoHeader and its DTO TipoHeaderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoHeaderMapper {

    TipoHeaderDTO tipoHeaderToTipoHeaderDTO(TipoHeader tipoHeader);

    List<TipoHeaderDTO> tipoHeadersToTipoHeaderDTOs(List<TipoHeader> tipoHeaders);

    @Mapping(target = "headers", ignore = true)
    TipoHeader tipoHeaderDTOToTipoHeader(TipoHeaderDTO tipoHeaderDTO);

    List<TipoHeader> tipoHeaderDTOsToTipoHeaders(List<TipoHeaderDTO> tipoHeaderDTOs);
}
