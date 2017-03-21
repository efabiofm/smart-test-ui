package com.quenti.smarttestui.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Alerta entity.
 */
public class AlertaDTO implements Serializable {

    private Long id;

    @NotNull
    private String mensaje;

    private Boolean activo = true;

    private Long tipoAlertaId;


    private String tipoAlertaMetodo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getTipoAlertaId() {
        return tipoAlertaId;
    }

    public void setTipoAlertaId(Long tipoAlertaId) {
        this.tipoAlertaId = tipoAlertaId;
    }


    public String getTipoAlertaMetodo() {
        return tipoAlertaMetodo;
    }

    public void setTipoAlertaMetodo(String tipoAlertaMetodo) {
        this.tipoAlertaMetodo = tipoAlertaMetodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlertaDTO alertaDTO = (AlertaDTO) o;

        if ( ! Objects.equals(id, alertaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlertaDTO{" +
            "id=" + id +
            ", mensaje='" + mensaje + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
