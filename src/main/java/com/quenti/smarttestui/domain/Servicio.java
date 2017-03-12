package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Servicio.
 */
@Entity
@Table(name = "servicio")
public class Servicio implements Serializable {

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
    @JoinTable(name = "servicio_metodo",
               joinColumns = @JoinColumn(name="servicios_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="metodos_id", referencedColumnName="ID"))
    private Set<Metodo> metodos = new HashSet<>();

    @OneToMany(mappedBy = "servicio")
    @JsonIgnore
    private Set<Prueba> pruebas = new HashSet<>();

    @ManyToMany(mappedBy = "servicios")
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

    public Servicio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public Servicio url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Servicio activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Metodo> getMetodos() {
        return metodos;
    }

    public Servicio metodos(Set<Metodo> metodos) {
        this.metodos = metodos;
        return this;
    }

    public Servicio addMetodo(Metodo metodo) {
        metodos.add(metodo);
        metodo.getServicios().add(this);
        return this;
    }

    public Servicio removeMetodo(Metodo metodo) {
        metodos.remove(metodo);
        metodo.getServicios().remove(this);
        return this;
    }

    public void setMetodos(Set<Metodo> metodos) {
        this.metodos = metodos;
    }

    public Set<Prueba> getPruebas() {
        return pruebas;
    }

    public Servicio pruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
        return this;
    }

    public Servicio addPrueba(Prueba prueba) {
        pruebas.add(prueba);
        prueba.setServicio(this);
        return this;
    }

    public Servicio removePrueba(Prueba prueba) {
        pruebas.remove(prueba);
        prueba.setServicio(null);
        return this;
    }

    public void setPruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
    }

    public Set<Modulo> getModulos() {
        return modulos;
    }

    public Servicio modulos(Set<Modulo> modulos) {
        this.modulos = modulos;
        return this;
    }

    public Servicio addModulo(Modulo modulo) {
        modulos.add(modulo);
        modulo.getServicios().add(this);
        return this;
    }

    public Servicio removeModulo(Modulo modulo) {
        modulos.remove(modulo);
        modulo.getServicios().remove(this);
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
        Servicio servicio = (Servicio) o;
        if (servicio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, servicio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Servicio{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", url='" + url + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
