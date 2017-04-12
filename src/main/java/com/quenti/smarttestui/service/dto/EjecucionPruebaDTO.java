package com.quenti.smarttestui.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the EjecucionPrueba entity.
 */
public class EjecucionPruebaDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    private Integer tiempoRespuesta;

    private String resultado;

    private Integer jhUserId;

    private String body;

    private Boolean activo;

    private String url;

    private Long pruebaId;

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
    public Integer getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(Integer tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }
    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public Integer getJhUserId() {
        return jhUserId;
    }

    public void setJhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
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

    public Long getPruebaId() {
        return pruebaId;
    }

    public void setPruebaId(Long pruebaId) {
        this.pruebaId = pruebaId;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EjecucionPruebaDTO ejecucionPruebaDTO = (EjecucionPruebaDTO) o;

        if ( ! Objects.equals(id, ejecucionPruebaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EjecucionPruebaDTO{" +
            "id=" + id +
            ", fecha='" + fecha + "'" +
            ", tiempoRespuesta='" + tiempoRespuesta + "'" +
            ", resultado='" + resultado + "'" +
            ", jhUserId='" + jhUserId + "'" +
            ", body='" + body + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
