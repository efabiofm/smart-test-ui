package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoEvento.
 */
@Entity
@Table(name = "tipo_evento")
public class TipoEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "tipoEvento")
    @JsonIgnore
    private Set<Bitacora> bitacoras = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoEvento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isActivo() {
        return activo;
    }

    public TipoEvento activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Bitacora> getBitacoras() {
        return bitacoras;
    }

    public TipoEvento bitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
        return this;
    }

    public TipoEvento addBitacora(Bitacora bitacora) {
        bitacoras.add(bitacora);
        bitacora.setTipoEvento(this);
        return this;
    }

    public TipoEvento removeBitacora(Bitacora bitacora) {
        bitacoras.remove(bitacora);
        bitacora.setTipoEvento(null);
        return this;
    }

    public void setBitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoEvento tipoEvento = (TipoEvento) o;
        if (tipoEvento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoEvento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoEvento{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
