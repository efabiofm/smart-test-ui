package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.BitacoraDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Bitacora and its DTO BitacoraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BitacoraMapper {

    @Mapping(source = "tipoEvento.id", target = "tipoEventoId")
    @Mapping(source = "tipoEvento.nombre", target = "tipoEventoNombre")
    BitacoraDTO bitacoraToBitacoraDTO(Bitacora bitacora);

    List<BitacoraDTO> bitacorasToBitacoraDTOs(List<Bitacora> bitacoras);

    @Mapping(source = "tipoEventoId", target = "tipoEvento")
    Bitacora bitacoraDTOToBitacora(BitacoraDTO bitacoraDTO);

    List<Bitacora> bitacoraDTOsToBitacoras(List<BitacoraDTO> bitacoraDTOs);

    default TipoEvento tipoEventoFromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setId(id);
        return tipoEvento;
    }
}
