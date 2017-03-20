package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.HeaderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Header and its DTO HeaderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HeaderMapper {

    @Mapping(source = "tipoHeader.id", target = "tipoHeaderId")
    @Mapping(source = "tipoHeader.nombre", target = "tipoHeaderNombre")
    @Mapping(source = "serviceProvider.id", target = "serviceProviderId")
    @Mapping(source = "serviceProvider.nombre", target = "serviceProviderNombre")
    HeaderDTO headerToHeaderDTO(Header header);

    List<HeaderDTO> headersToHeaderDTOs(List<Header> headers);

    @Mapping(source = "tipoHeaderId", target = "tipoHeader")
    @Mapping(source = "serviceProviderId", target = "serviceProvider")
    Header headerDTOToHeader(HeaderDTO headerDTO);

    List<Header> headerDTOsToHeaders(List<HeaderDTO> headerDTOs);

    default TipoHeader tipoHeaderFromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoHeader tipoHeader = new TipoHeader();
        tipoHeader.setId(id);
        return tipoHeader;
    }

    default ServiceProvider serviceProviderFromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(id);
        return serviceProvider;
    }
}
