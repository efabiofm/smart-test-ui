package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Modulo entity.
 */
public class ModuloDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String url;

    private Boolean activo;


    private Set<AmbienteDTO> ambientes = new HashSet<>();

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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<AmbienteDTO> getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(Set<AmbienteDTO> ambientes) {
        this.ambientes = ambientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModuloDTO moduloDTO = (ModuloDTO) o;

        if ( ! Objects.equals(id, moduloDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModuloDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", url='" + url + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
