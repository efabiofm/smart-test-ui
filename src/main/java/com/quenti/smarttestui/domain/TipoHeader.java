package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoHeader.
 */
@Entity
@Table(name = "tipo_header")
public class TipoHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "tipoHeader")
    @JsonIgnore
    private Set<Header> headers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoHeader nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isActivo() {
        return activo;
    }

    public TipoHeader activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Header> getHeaders() {
        return headers;
    }

    public TipoHeader headers(Set<Header> headers) {
        this.headers = headers;
        return this;
    }

    public TipoHeader addHeader(Header header) {
        headers.add(header);
        header.setTipoHeader(this);
        return this;
    }

    public TipoHeader removeHeader(Header header) {
        headers.remove(header);
        header.setTipoHeader(null);
        return this;
    }

    public void setHeaders(Set<Header> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoHeader tipoHeader = (TipoHeader) o;
        if (tipoHeader.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoHeader.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoHeader{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
