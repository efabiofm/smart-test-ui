package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.ServicioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Servicio and its DTO ServicioDTO.
 */
@Mapper(componentModel = "spring", uses = {MetodoMapper.class, })
public interface ServicioMapper {

    ServicioDTO servicioToServicioDTO(Servicio servicio);

    List<ServicioDTO> serviciosToServicioDTOs(List<Servicio> servicios);

    @Mapping(target = "pruebas", ignore = true)
    @Mapping(target = "modulos", ignore = true)
    Servicio servicioDTOToServicio(ServicioDTO servicioDTO);

    List<Servicio> servicioDTOsToServicios(List<ServicioDTO> servicioDTOs);

    default Metodo metodoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Metodo metodo = new Metodo();
        metodo.setId(id);
        return metodo;
    }
}
