package com.quenti.smarttestui.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Seguridad entity.
 */
public class SeguridadDTO implements Serializable {

    private Long id;

    @NotNull
    private String token;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Long jhUserId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Long getJhUserId() {
        return jhUserId;
    }

    public void setJhUserId(Long jhUserId) {
        this.jhUserId = jhUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeguridadDTO seguridadDTO = (SeguridadDTO) o;

        if ( ! Objects.equals(id, seguridadDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SeguridadDTO{" +
            "id=" + id +
            ", token='" + token + "'" +
            ", fecha='" + fecha + "'" +
            ", jhUserId='" + jhUserId + "'" +
            '}';
    }
}
