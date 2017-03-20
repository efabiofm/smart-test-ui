package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Metodo entity.
 */
public class MetodoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String url;

    private Boolean activo;


    private Set<ServicioDTO> servicios = new HashSet<>();

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

    public Set<ServicioDTO> getServicios() {
        return servicios;
    }

    public void setServicios(Set<ServicioDTO> servicios) {
        this.servicios = servicios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetodoDTO metodoDTO = (MetodoDTO) o;

        if ( ! Objects.equals(id, metodoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MetodoDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", url='" + url + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
