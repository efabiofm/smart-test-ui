package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Prueba entity.
 */
public class PruebaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String body;

    private Boolean activo;


    private Long ambienteId;
    

    private String ambienteNombre;

    private Long moduloId;
    

    private String moduloNombre;

    private Long servicioId;
    

    private String servicioNombre;

    private Long metodoId;
    

    private String metodoNombre;

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
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getAmbienteId() {
        return ambienteId;
    }

    public void setAmbienteId(Long ambienteId) {
        this.ambienteId = ambienteId;
    }


    public String getAmbienteNombre() {
        return ambienteNombre;
    }

    public void setAmbienteNombre(String ambienteNombre) {
        this.ambienteNombre = ambienteNombre;
    }

    public Long getModuloId() {
        return moduloId;
    }

    public void setModuloId(Long moduloId) {
        this.moduloId = moduloId;
    }


    public String getModuloNombre() {
        return moduloNombre;
    }

    public void setModuloNombre(String moduloNombre) {
        this.moduloNombre = moduloNombre;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }


    public String getServicioNombre() {
        return servicioNombre;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }

    public Long getMetodoId() {
        return metodoId;
    }

    public void setMetodoId(Long metodoId) {
        this.metodoId = metodoId;
    }


    public String getMetodoNombre() {
        return metodoNombre;
    }

    public void setMetodoNombre(String metodoNombre) {
        this.metodoNombre = metodoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PruebaDTO pruebaDTO = (PruebaDTO) o;

        if ( ! Objects.equals(id, pruebaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PruebaDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", body='" + body + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
