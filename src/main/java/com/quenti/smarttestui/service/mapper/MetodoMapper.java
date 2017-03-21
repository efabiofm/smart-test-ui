package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.MetodoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Metodo and its DTO MetodoDTO.
 */
@Mapper(componentModel = "spring", uses = {ServicioMapper.class, })
public interface MetodoMapper {

    MetodoDTO metodoToMetodoDTO(Metodo metodo);

    List<MetodoDTO> metodosToMetodoDTOs(List<Metodo> metodos);

    @Mapping(target = "pruebas", ignore = true)
    @Mapping(target = "parametros", ignore = true)
    Metodo metodoDTOToMetodo(MetodoDTO metodoDTO);

    List<Metodo> metodoDTOsToMetodos(List<MetodoDTO> metodoDTOs);

    default Servicio servicioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Servicio servicio = new Servicio();
        servicio.setId(id);
        return servicio;
    }
}
