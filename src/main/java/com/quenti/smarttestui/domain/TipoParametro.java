package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoParametro.
 */
@Entity
@Table(name = "tipo_parametro")
public class TipoParametro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "tipoParametro")
    @JsonIgnore
    private Set<Parametro> parametros = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoParametro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isActivo() {
        return activo;
    }

    public TipoParametro activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Parametro> getParametros() {
        return parametros;
    }

    public TipoParametro parametros(Set<Parametro> parametros) {
        this.parametros = parametros;
        return this;
    }

    public TipoParametro addParametro(Parametro parametro) {
        parametros.add(parametro);
        parametro.setTipoParametro(this);
        return this;
    }

    public TipoParametro removeParametro(Parametro parametro) {
        parametros.remove(parametro);
        parametro.setTipoParametro(null);
        return this;
    }

    public void setParametros(Set<Parametro> parametros) {
        this.parametros = parametros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoParametro tipoParametro = (TipoParametro) o;
        if (tipoParametro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoParametro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoParametro{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
