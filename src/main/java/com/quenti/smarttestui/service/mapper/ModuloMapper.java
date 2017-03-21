package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.ModuloDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Modulo and its DTO ModuloDTO.
 */
@Mapper(componentModel = "spring", uses = {AmbienteMapper.class, })
public interface ModuloMapper {

    ModuloDTO moduloToModuloDTO(Modulo modulo);

    List<ModuloDTO> modulosToModuloDTOs(List<Modulo> modulos);

    @Mapping(target = "pruebas", ignore = true)
    @Mapping(target = "servicios", ignore = true)
    Modulo moduloDTOToModulo(ModuloDTO moduloDTO);

    List<Modulo> moduloDTOsToModulos(List<ModuloDTO> moduloDTOs);

    default Ambiente ambienteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Ambiente ambiente = new Ambiente();
        ambiente.setId(id);
        return ambiente;
    }
}
