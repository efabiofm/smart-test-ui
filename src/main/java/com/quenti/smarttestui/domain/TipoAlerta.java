package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoAlerta.
 */
@Entity
@Table(name = "tipo_alerta")
public class TipoAlerta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "metodo", nullable = false)
    private String metodo;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "tipoAlerta")
    @JsonIgnore
    private Set<Alerta> alertas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public TipoAlerta metodo(String metodo) {
        this.metodo = metodo;
        return this;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Boolean isActivo() {
        return activo;
    }

    public TipoAlerta activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Alerta> getAlertas() {
        return alertas;
    }

    public TipoAlerta alertas(Set<Alerta> alertas) {
        this.alertas = alertas;
        return this;
    }

    public TipoAlerta addAlerta(Alerta alerta) {
        alertas.add(alerta);
        alerta.setTipoAlerta(this);
        return this;
    }

    public TipoAlerta removeAlerta(Alerta alerta) {
        alertas.remove(alerta);
        alerta.setTipoAlerta(null);
        return this;
    }

    public void setAlertas(Set<Alerta> alertas) {
        this.alertas = alertas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoAlerta tipoAlerta = (TipoAlerta) o;
        if (tipoAlerta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoAlerta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoAlerta{" +
            "id=" + id +
            ", metodo='" + metodo + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
