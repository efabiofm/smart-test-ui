package com.quenti.smarttestui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Alerta.
 */
@Entity
@Table(name = "alerta")
public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    private TipoAlerta tipoAlerta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Alerta mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Alerta activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public Alerta tipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
        return this;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alerta alerta = (Alerta) o;
        if (alerta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alerta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alerta{" +
            "id=" + id +
            ", mensaje='" + mensaje + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
