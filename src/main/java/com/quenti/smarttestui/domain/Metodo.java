package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Metodo.
 */
@Entity
@Table(name = "metodo")
public class Metodo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToMany
    @JoinTable(name = "metodo_servicio",
               joinColumns = @JoinColumn(name="metodos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="servicios_id", referencedColumnName="ID"))
    private Set<Servicio> servicios = new HashSet<>();

    @OneToMany(mappedBy = "metodo")
    @JsonIgnore
    private Set<Prueba> pruebas = new HashSet<>();

    @ManyToMany(mappedBy = "metodos")
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

    public Metodo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public Metodo url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Metodo activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public Metodo servicios(Set<Servicio> servicios) {
        this.servicios = servicios;
        return this;
    }

    public Metodo addServicio(Servicio servicio) {
        servicios.add(servicio);
        servicio.getMetodos().add(this);
        return this;
    }

    public Metodo removeServicio(Servicio servicio) {
        servicios.remove(servicio);
        servicio.getMetodos().remove(this);
        return this;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Set<Prueba> getPruebas() {
        return pruebas;
    }

    public Metodo pruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
        return this;
    }

    public Metodo addPrueba(Prueba prueba) {
        pruebas.add(prueba);
        prueba.setMetodo(this);
        return this;
    }

    public Metodo removePrueba(Prueba prueba) {
        pruebas.remove(prueba);
        prueba.setMetodo(null);
        return this;
    }

    public void setPruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
    }

    public Set<Parametro> getParametros() {
        return parametros;
    }

    public Metodo parametros(Set<Parametro> parametros) {
        this.parametros = parametros;
        return this;
    }

    public Metodo addParametro(Parametro parametro) {
        parametros.add(parametro);
        parametro.getMetodos().add(this);
        return this;
    }

    public Metodo removeParametro(Parametro parametro) {
        parametros.remove(parametro);
        parametro.getMetodos().remove(this);
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
        Metodo metodo = (Metodo) o;
        if (metodo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, metodo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Metodo{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", url='" + url + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
