package com.quenti.smarttestui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Parametro.
 */
@Entity
@Table(name = "parametro")
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "valor", nullable = false)
    private String valor;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    private TipoParametro tipoParametro;

    @ManyToMany
    @JoinTable(name = "parametro_metodo",
               joinColumns = @JoinColumn(name="parametros_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="metodos_id", referencedColumnName="ID"))
    private Set<Metodo> metodos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Parametro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public Parametro valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Parametro activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public TipoParametro getTipoParametro() {
        return tipoParametro;
    }

    public Parametro tipoParametro(TipoParametro tipoParametro) {
        this.tipoParametro = tipoParametro;
        return this;
    }

    public void setTipoParametro(TipoParametro tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    public Set<Metodo> getMetodos() {
        return metodos;
    }

    public Parametro metodos(Set<Metodo> metodos) {
        this.metodos = metodos;
        return this;
    }

    public Parametro addMetodo(Metodo metodo) {
        metodos.add(metodo);
        metodo.getParametros().add(this);
        return this;
    }

    public Parametro removeMetodo(Metodo metodo) {
        metodos.remove(metodo);
        metodo.getParametros().remove(this);
        return this;
    }

    public void setMetodos(Set<Metodo> metodos) {
        this.metodos = metodos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parametro parametro = (Parametro) o;
        if (parametro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, parametro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Parametro{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
