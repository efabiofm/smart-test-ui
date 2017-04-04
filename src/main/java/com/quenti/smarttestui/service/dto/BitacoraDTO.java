package com.quenti.smarttestui.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Bitacora entity.
 */
public class BitacoraDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Integer jhUserId;

    @NotNull
    private String descripcion;


    private Long tipoEventoId;
    

    private String tipoEventoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Integer getJhUserId() {
        return jhUserId;
    }

    public void setJhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTipoEventoId() {
        return tipoEventoId;
    }

    public void setTipoEventoId(Long tipoEventoId) {
        this.tipoEventoId = tipoEventoId;
    }


    public String getTipoEventoNombre() {
        return tipoEventoNombre;
    }

    public void setTipoEventoNombre(String tipoEventoNombre) {
        this.tipoEventoNombre = tipoEventoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BitacoraDTO bitacoraDTO = (BitacoraDTO) o;

        if ( ! Objects.equals(id, bitacoraDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BitacoraDTO{" +
            "id=" + id +
            ", fecha='" + fecha + "'" +
            ", jhUserId='" + jhUserId + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
