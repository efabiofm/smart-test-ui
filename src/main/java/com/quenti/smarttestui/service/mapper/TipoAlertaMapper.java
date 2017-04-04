package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.TipoAlertaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TipoAlerta and its DTO TipoAlertaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoAlertaMapper {

    TipoAlertaDTO tipoAlertaToTipoAlertaDTO(TipoAlerta tipoAlerta);

    List<TipoAlertaDTO> tipoAlertasToTipoAlertaDTOs(List<TipoAlerta> tipoAlertas);

    @Mapping(target = "alertas", ignore = true)
    TipoAlerta tipoAlertaDTOToTipoAlerta(TipoAlertaDTO tipoAlertaDTO);

    List<TipoAlerta> tipoAlertaDTOsToTipoAlertas(List<TipoAlertaDTO> tipoAlertaDTOs);
}
