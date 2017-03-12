package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Parametro entity.
 */
public class ParametroDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;


    private Long tipoParametroId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTipoParametroId() {
        return tipoParametroId;
    }

    public void setTipoParametroId(Long tipoParametroId) {
        this.tipoParametroId = tipoParametroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParametroDTO parametroDTO = (ParametroDTO) o;

        if ( ! Objects.equals(id, parametroDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParametroDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
