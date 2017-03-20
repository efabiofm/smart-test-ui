package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TipoAlerta entity.
 */
public class TipoAlertaDTO implements Serializable {

    private Long id;

    @NotNull
    private String metodo;

    private Boolean activo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
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

        TipoAlertaDTO tipoAlertaDTO = (TipoAlertaDTO) o;

        if ( ! Objects.equals(id, tipoAlertaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoAlertaDTO{" +
            "id=" + id +
            ", metodo='" + metodo + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
