package com.quenti.smarttestui.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime fecha;

    @Column(name = "tiempo_respuesta")
    private Long tiempoRespuesta;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "jh_user_id")
    private Integer jhUserId;

    @Column(name = "body")
    private String body;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "url")
    private String url;

    @Column(name = "estado")
    private String estado;

    @Column(name = "serviceProviderId")
    private Long serviceProviderId;

    @Column(name = "serviceGroupId")
    private Long serviceGroupId;

    @Column(name = "jhUserName")
    private String jhUserName;

    @ManyToOne
    private Prueba prueba;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EjecucionPrueba fecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public EjecucionPrueba tiempoRespuesta(Long tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
        return this;
    }

    public void setTiempoRespuesta(Long tiempoRespuesta) {
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

    public String getUrl() {
        return url;
    }

    public EjecucionPrueba url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEstado() {
        return estado;
    }

    public EjecucionPrueba estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getJhUserName() {
        return jhUserName;
    }

    public EjecucionPrueba jhUserName(String jhUserName) {
        this.jhUserName = jhUserName;
        return this;
    }

    public void setJhUserName(String jhUserName) {
        this.jhUserName = jhUserName;
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

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public EjecucionPrueba serviceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
        return this;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Long getServiceGroupId() {
        return serviceGroupId;
    }

    public EjecucionPrueba serviceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
        return this;
    }

    public void setServiceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
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
