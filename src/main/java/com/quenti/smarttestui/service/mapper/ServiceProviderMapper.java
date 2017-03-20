package com.quenti.smarttestui.service.mapper;

import com.quenti.smarttestui.domain.*;
import com.quenti.smarttestui.service.dto.ServiceProviderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ServiceProvider and its DTO ServiceProviderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceProviderMapper {

    ServiceProviderDTO serviceProviderToServiceProviderDTO(ServiceProvider serviceProvider);

    List<ServiceProviderDTO> serviceProvidersToServiceProviderDTOs(List<ServiceProvider> serviceProviders);

    @Mapping(target = "headers", ignore = true)
    ServiceProvider serviceProviderDTOToServiceProvider(ServiceProviderDTO serviceProviderDTO);

    List<ServiceProvider> serviceProviderDTOsToServiceProviders(List<ServiceProviderDTO> serviceProviderDTOs);
}
