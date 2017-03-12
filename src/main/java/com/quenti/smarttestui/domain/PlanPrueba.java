package com.quenti.smarttestui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PlanPrueba.
 */
@Entity
@Table(name = "plan_prueba")
public class PlanPrueba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "plan_prueba_prueba",
               joinColumns = @JoinColumn(name="plan_pruebas_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="pruebas_id", referencedColumnName="ID"))
    private Set<Prueba> pruebas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public PlanPrueba nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Prueba> getPruebas() {
        return pruebas;
    }

    public PlanPrueba pruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
        return this;
    }

    public PlanPrueba addPrueba(Prueba prueba) {
        pruebas.add(prueba);
        prueba.getPlanPruebas().add(this);
        return this;
    }

    public PlanPrueba removePrueba(Prueba prueba) {
        pruebas.remove(prueba);
        prueba.getPlanPruebas().remove(this);
        return this;
    }

    public void setPruebas(Set<Prueba> pruebas) {
        this.pruebas = pruebas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlanPrueba planPrueba = (PlanPrueba) o;
        if (planPrueba.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, planPrueba.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PlanPrueba{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
