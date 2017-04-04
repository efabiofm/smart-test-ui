package com.quenti.smarttestui.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EjecucionPrueba.
 */
@Entity
@Table(name = "ejecucion_prueba")
public class EjecucionPrueba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "tiempo_respuesta")
    private Integer tiempoRespuesta;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "jh_user_id")
    private Integer jhUserId;

    @Column(name = "body")
    private String body;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    private Prueba prueba;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EjecucionPrueba fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public EjecucionPrueba tiempoRespuesta(Integer tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
        return this;
    }

    public void setTiempoRespuesta(Integer tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }

    public String getResultado() {
        return resultado;
    }

    public EjecucionPrueba resultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Integer getJhUserId() {
        return jhUserId;
    }

    public EjecucionPrueba jhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
        return this;
    }

    public void setJhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
    }

    public String getBody() {
        return body;
    }

    public EjecucionPrueba body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean isActivo() {
        return activo;
    }

    public EjecucionPrueba activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public EjecucionPrueba prueba(Prueba prueba) {
        this.prueba = prueba;
        return this;
    }

    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EjecucionPrueba ejecucionPrueba = (EjecucionPrueba) o;
        if (ejecucionPrueba.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ejecucionPrueba.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EjecucionPrueba{" +
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
