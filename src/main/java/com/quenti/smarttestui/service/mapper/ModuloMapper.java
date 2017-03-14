package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.ModuloDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Modulo and its DTO ModuloDTO.
 */
@Mapper(componentModel = "spring", uses = {ServicioMapper.class, })
public interface ModuloMapper {

    ModuloDTO moduloToModuloDTO(Modulo modulo);

    List<ModuloDTO> modulosToModuloDTOs(List<Modulo> modulos);

    @Mapping(target = "pruebas", ignore = true)
    @Mapping(target = "ambientes", ignore = true)
    Modulo moduloDTOToModulo(ModuloDTO moduloDTO);

    List<Modulo> moduloDTOsToModulos(List<ModuloDTO> moduloDTOs);

    default Servicio servicioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Servicio servicio = new Servicio();
        servicio.setId(id);
        return servicio;
    }
}
