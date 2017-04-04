package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ambiente.
 */
@Entity
@Table(name = "ambiente")
public class Ambiente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "ambiente")
    @JsonIgnore
    private Set<Prueba> pruebas = new HashSet<>();

    @ManyToMany(mappedBy = "ambientes")
    @JsonIgnore
    private Set<Modulo> modulos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Ambiente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Ambiente descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Ambiente activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Prueba> getPruebas() {
        return pruebas;
    }

    public Ambiente pruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
        return this;
    }

    public Ambiente addPrueba(Prueba prueba) {
        pruebas.add(prueba);
        prueba.setAmbiente(this);
        return this;
    }

    public Ambiente removePrueba(Prueba prueba) {
        pruebas.remove(prueba);
        prueba.setAmbiente(null);
        return this;
    }

    public void setPruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
    }

    public Set<Modulo> getModulos() {
        return modulos;
    }

    public Ambiente modulos(Set<Modulo> modulos) {
        this.modulos = modulos;
        return this;
    }

    public Ambiente addModulo(Modulo modulo) {
        modulos.add(modulo);
        modulo.getAmbientes().add(this);
        return this;
    }

    public Ambiente removeModulo(Modulo modulo) {
        modulos.remove(modulo);
        modulo.getAmbientes().remove(this);
        return this;
    }

    public void setModulos(Set<Modulo> modulos) {
        this.modulos = modulos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ambiente ambiente = (Ambiente) o;
        if (ambiente.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ambiente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ambiente{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
