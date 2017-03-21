package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Modulo.
 */
@Entity
@Table(name = "modulo")
public class Modulo implements Serializable {

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
    @JoinTable(name = "modulo_ambiente",
               joinColumns = @JoinColumn(name="modulos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="ambientes_id", referencedColumnName="ID"))
    private Set<Ambiente> ambientes = new HashSet<>();

    @OneToMany(mappedBy = "modulo")
    @JsonIgnore
    private Set<Prueba> pruebas = new HashSet<>();

    @ManyToMany(mappedBy = "modulos")
    @JsonIgnore
    private Set<Servicio> servicios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Modulo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public Modulo url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Modulo activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Ambiente> getAmbientes() {
        return ambientes;
    }

    public Modulo ambientes(Set<Ambiente> ambientes) {
        this.ambientes = ambientes;
        return this;
    }

    public Modulo addAmbiente(Ambiente ambiente) {
        ambientes.add(ambiente);
        ambiente.getModulos().add(this);
        return this;
    }

    public Modulo removeAmbiente(Ambiente ambiente) {
        ambientes.remove(ambiente);
        ambiente.getModulos().remove(this);
        return this;
    }

    public void setAmbientes(Set<Ambiente> ambientes) {
        this.ambientes = ambientes;
    }

    public Set<Prueba> getPruebas() {
        return pruebas;
    }

    public Modulo pruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
        return this;
    }

    public Modulo addPrueba(Prueba prueba) {
        pruebas.add(prueba);
        prueba.setModulo(this);
        return this;
    }

    public Modulo removePrueba(Prueba prueba) {
        pruebas.remove(prueba);
        prueba.setModulo(null);
        return this;
    }

    public void setPruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public Modulo servicios(Set<Servicio> servicios) {
        this.servicios = servicios;
        return this;
    }

    public Modulo addServicio(Servicio servicio) {
        servicios.add(servicio);
        servicio.getModulos().add(this);
        return this;
    }

    public Modulo removeServicio(Servicio servicio) {
        servicios.remove(servicio);
        servicio.getModulos().remove(this);
        return this;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Modulo modulo = (Modulo) o;
        if (modulo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, modulo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Modulo{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", url='" + url + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
