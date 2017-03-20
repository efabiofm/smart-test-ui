package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.ParametroDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Parametro and its DTO ParametroDTO.
 */
@Mapper(componentModel = "spring", uses = {MetodoMapper.class, })
public interface ParametroMapper {

    @Mapping(source = "tipoParametro.id", target = "tipoParametroId")
    ParametroDTO parametroToParametroDTO(Parametro parametro);

    List<ParametroDTO> parametrosToParametroDTOs(List<Parametro> parametros);

    @Mapping(source = "tipoParametroId", target = "tipoParametro")
    Parametro parametroDTOToParametro(ParametroDTO parametroDTO);

    List<Parametro> parametroDTOsToParametros(List<ParametroDTO> parametroDTOs);

    default TipoParametro tipoParametroFromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoParametro tipoParametro = new TipoParametro();
        tipoParametro.setId(id);
        return tipoParametro;
    }

    default Metodo metodoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Metodo metodo = new Metodo();
        metodo.setId(id);
        return metodo;
    }
}
