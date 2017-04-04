package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the PlanPrueba entity.
 */
public class PlanPruebaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private Boolean activo;


    private Set<PruebaDTO> pruebas = new HashSet<>();

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
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<PruebaDTO> getPruebas() {
        return pruebas;
    }

    public void setPruebas(Set<PruebaDTO> pruebas) {
        this.pruebas = pruebas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlanPruebaDTO planPruebaDTO = (PlanPruebaDTO) o;

        if ( ! Objects.equals(id, planPruebaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PlanPruebaDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
