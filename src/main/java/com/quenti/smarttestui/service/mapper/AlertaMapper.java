package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.AlertaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Alerta and its DTO AlertaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlertaMapper {

    @Mapping(source = "tipoAlerta.id", target = "tipoAlertaId")
    @Mapping(source = "tipoAlerta.metodo", target = "tipoAlertaMetodo")
    AlertaDTO alertaToAlertaDTO(Alerta alerta);

    List<AlertaDTO> alertasToAlertaDTOs(List<Alerta> alertas);

    @Mapping(source = "tipoAlertaId", target = "tipoAlerta")
    Alerta alertaDTOToAlerta(AlertaDTO alertaDTO);

    List<Alerta> alertaDTOsToAlertas(List<AlertaDTO> alertaDTOs);

    default TipoAlerta tipoAlertaFromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoAlerta tipoAlerta = new TipoAlerta();
        tipoAlerta.setId(id);
        return tipoAlerta;
    }
}
