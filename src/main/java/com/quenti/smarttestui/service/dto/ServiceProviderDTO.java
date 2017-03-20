package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ServiceProvider entity.
 */
public class ServiceProviderDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer serviceProviderId;

    @NotNull
    private String nombre;

    private Boolean activo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceProviderDTO serviceProviderDTO = (ServiceProviderDTO) o;

        if ( ! Objects.equals(id, serviceProviderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ServiceProviderDTO{" +
            "id=" + id +
            ", serviceProviderId='" + serviceProviderId + "'" +
            ", nombre='" + nombre + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
