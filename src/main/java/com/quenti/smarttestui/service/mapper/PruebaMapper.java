package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.PruebaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Prueba and its DTO PruebaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PruebaMapper {

    @Mapping(source = "ambiente.id", target = "ambienteId")
    @Mapping(source = "ambiente.nombre", target = "ambienteNombre")
    @Mapping(source = "modulo.id", target = "moduloId")
    @Mapping(source = "modulo.nombre", target = "moduloNombre")
    @Mapping(source = "servicio.id", target = "servicioId")
    @Mapping(source = "servicio.nombre", target = "servicioNombre")
    @Mapping(source = "metodo.id", target = "metodoId")
    @Mapping(source = "metodo.nombre", target = "metodoNombre")
    PruebaDTO pruebaToPruebaDTO(Prueba prueba);

    List<PruebaDTO> pruebasToPruebaDTOs(List<Prueba> pruebas);

    @Mapping(source = "ambienteId", target = "ambiente")
    @Mapping(source = "moduloId", target = "modulo")
    @Mapping(source = "servicioId", target = "servicio")
    @Mapping(source = "metodoId", target = "metodo")
    @Mapping(target = "ejecucionPruebas", ignore = true)
    @Mapping(target = "planPruebas", ignore = true)
    Prueba pruebaDTOToPrueba(PruebaDTO pruebaDTO);

    List<Prueba> pruebaDTOsToPruebas(List<PruebaDTO> pruebaDTOs);

    default Ambiente ambienteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Ambiente ambiente = new Ambiente();
        ambiente.setId(id);
        return ambiente;
    }

    default Modulo moduloFromId(Long id) {
        if (id == null) {
            return null;
        }
        Modulo modulo = new Modulo();
        modulo.setId(id);
        return modulo;
    }

    default Servicio servicioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Servicio servicio = new Servicio();
        servicio.setId(id);
        return servicio;
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
