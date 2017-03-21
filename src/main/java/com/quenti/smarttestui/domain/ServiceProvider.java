package com.quenti.smarttestui.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ServiceProvider.
 */
@Entity
@Table(name = "service_provider")
public class ServiceProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "service_provider_id", nullable = false)
    private Integer serviceProviderId;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "serviceProvider")
    @JsonIgnore
    private Set<Header> headers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    public ServiceProvider serviceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
        return this;
    }

    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getNombre() {
        return nombre;
    }

    public ServiceProvider nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isActivo() {
        return activo;
    }

    public ServiceProvider activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Header> getHeaders() {
        return headers;
    }

    public ServiceProvider headers(Set<Header> headers) {
        this.headers = headers;
        return this;
    }

    public ServiceProvider addHeader(Header header) {
        headers.add(header);
        header.setServiceProvider(this);
        return this;
    }

    public ServiceProvider removeHeader(Header header) {
        headers.remove(header);
        header.setServiceProvider(null);
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
        ServiceProvider serviceProvider = (ServiceProvider) o;
        if (serviceProvider.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, serviceProvider.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
            "id=" + id +
            ", serviceProviderId='" + serviceProviderId + "'" +
            ", nombre='" + nombre + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
