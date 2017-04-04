package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.ServicioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Servicio and its DTO ServicioDTO.
 */
@Mapper(componentModel = "spring", uses = {ModuloMapper.class, })
public interface ServicioMapper {

    ServicioDTO servicioToServicioDTO(Servicio servicio);

    List<ServicioDTO> serviciosToServicioDTOs(List<Servicio> servicios);

    @Mapping(target = "pruebas", ignore = true)
    @Mapping(target = "metodos", ignore = true)
    Servicio servicioDTOToServicio(ServicioDTO servicioDTO);

    List<Servicio> servicioDTOsToServicios(List<ServicioDTO> servicioDTOs);

    default Modulo moduloFromId(Long id) {
        if (id == null) {
            return null;
        }
        Modulo modulo = new Modulo();
        modulo.setId(id);
        return modulo;
    }
}
