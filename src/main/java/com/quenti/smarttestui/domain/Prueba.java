package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Prueba.
 */
@Entity
@Table(name = "prueba")
public class Prueba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "serviceProviderId", nullable = true)
    private Long serviceProviderId;

    @Column(name = "serviceGroupId", nullable = true)
    private Long serviceGroupId;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    private Ambiente ambiente;

    @ManyToOne
    private Modulo modulo;

    @ManyToOne
    private Servicio servicio;

    @ManyToOne
    private Metodo metodo;

    @OneToMany(mappedBy = "prueba")
    @JsonIgnore
    private Set<EjecucionPrueba> ejecucionPruebas = new HashSet<>();

    @ManyToMany(mappedBy = "pruebas")
    @JsonIgnore
    private Set<PlanPrueba> planPruebas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public Prueba body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Prueba activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public Prueba serviceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
        return this;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Long getServiceGroupId() {
        return serviceGroupId;
    }

    public Prueba serviceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
        return this;
    }

    public void setServiceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public Prueba ambiente(Ambiente ambiente) {
        this.ambiente = ambiente;
        return this;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public Prueba modulo(Modulo modulo) {
        this.modulo = modulo;
        return this;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Prueba servicio(Servicio servicio) {
        this.servicio = servicio;
        return this;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Metodo getMetodo() {
        return metodo;
    }

    public Prueba metodo(Metodo metodo) {
        this.metodo = metodo;
        return this;
    }

    public void setMetodo(Metodo metodo) {
        this.metodo = metodo;
    }

    public Set<EjecucionPrueba> getEjecucionPruebas() {
        return ejecucionPruebas;
    }

    public Prueba ejecucionPruebas(Set<EjecucionPrueba> ejecucionPruebas) {
        this.ejecucionPruebas = ejecucionPruebas;
        return this;
    }

    public Prueba addEjecucionPrueba(EjecucionPrueba ejecucionPrueba) {
        ejecucionPruebas.add(ejecucionPrueba);
        ejecucionPrueba.setPrueba(this);
        return this;
    }

    public Prueba removeEjecucionPrueba(EjecucionPrueba ejecucionPrueba) {
        ejecucionPruebas.remove(ejecucionPrueba);
        ejecucionPrueba.setPrueba(null);
        return this;
    }

    public void setEjecucionPruebas(Set<EjecucionPrueba> ejecucionPruebas) {
        this.ejecucionPruebas = ejecucionPruebas;
    }

    public Set<PlanPrueba> getPlanPruebas() {
        return planPruebas;
    }

    public Prueba planPruebas(Set<PlanPrueba> planPruebas) {
        this.planPruebas = planPruebas;
        return this;
    }

    public Prueba addPlanPrueba(PlanPrueba planPrueba) {
        planPruebas.add(planPrueba);
        planPrueba.getPruebas().add(this);
        return this;
    }

    public Prueba removePlanPrueba(PlanPrueba planPrueba) {
        planPruebas.remove(planPrueba);
        planPrueba.getPruebas().remove(this);
        return this;
    }

    public void setPlanPruebas(Set<PlanPrueba> planPruebas) {
        this.planPruebas = planPruebas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prueba prueba = (Prueba) o;
        if (prueba.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, prueba.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Prueba{" +
            "id=" + id +
            ", body='" + body + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
