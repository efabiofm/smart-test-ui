package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.MetodoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Metodo and its DTO MetodoDTO.
 */
@Mapper(componentModel = "spring", uses = {ParametroMapper.class, })
public interface MetodoMapper {

    MetodoDTO metodoToMetodoDTO(Metodo metodo);

    List<MetodoDTO> metodosToMetodoDTOs(List<Metodo> metodos);

    @Mapping(target = "pruebas", ignore = true)
    @Mapping(target = "servicios", ignore = true)
    Metodo metodoDTOToMetodo(MetodoDTO metodoDTO);

    List<Metodo> metodoDTOsToMetodos(List<MetodoDTO> metodoDTOs);

    default Parametro parametroFromId(Long id) {
        if (id == null) {
            return null;
        }
        Parametro parametro = new Parametro();
        parametro.setId(id);
        return parametro;
    }
}
