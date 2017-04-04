package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Header entity.
 */
public class HeaderDTO implements Serializable {

    private Long id;

    private Integer serviceGroupId;

    @NotNull
    private String token;

    private Boolean activo;


    private Long tipoHeaderId;
    

    private String tipoHeaderNombre;

    private Long serviceProviderId;
    

    private String serviceProviderNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(Integer serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getTipoHeaderId() {
        return tipoHeaderId;
    }

    public void setTipoHeaderId(Long tipoHeaderId) {
        this.tipoHeaderId = tipoHeaderId;
    }


    public String getTipoHeaderNombre() {
        return tipoHeaderNombre;
    }

    public void setTipoHeaderNombre(String tipoHeaderNombre) {
        this.tipoHeaderNombre = tipoHeaderNombre;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }


    public String getServiceProviderNombre() {
        return serviceProviderNombre;
    }

    public void setServiceProviderNombre(String serviceProviderNombre) {
        this.serviceProviderNombre = serviceProviderNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HeaderDTO headerDTO = (HeaderDTO) o;

        if ( ! Objects.equals(id, headerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeaderDTO{" +
            "id=" + id +
            ", serviceGroupId='" + serviceGroupId + "'" +
            ", token='" + token + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
