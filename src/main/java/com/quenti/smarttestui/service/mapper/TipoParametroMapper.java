package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.TipoParametroDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TipoParametro and its DTO TipoParametroDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoParametroMapper {

    TipoParametroDTO tipoParametroToTipoParametroDTO(TipoParametro tipoParametro);

    List<TipoParametroDTO> tipoParametrosToTipoParametroDTOs(List<TipoParametro> tipoParametros);

    @Mapping(target = "parametros", ignore = true)
    TipoParametro tipoParametroDTOToTipoParametro(TipoParametroDTO tipoParametroDTO);

    List<TipoParametro> tipoParametroDTOsToTipoParametros(List<TipoParametroDTO> tipoParametroDTOs);
}
