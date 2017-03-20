package com.quenti.smarttestui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Header.
 */
@Entity
@Table(name = "header")
public class Header implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "service_group_id")
    private Integer serviceGroupId;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    private TipoHeader tipoHeader;

    @ManyToOne
    private ServiceProvider serviceProvider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getServiceGroupId() {
        return serviceGroupId;
    }

    public Header serviceGroupId(Integer serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
        return this;
    }

    public void setServiceGroupId(Integer serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public String getToken() {
        return token;
    }

    public Header token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Header activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public TipoHeader getTipoHeader() {
        return tipoHeader;
    }

    public Header tipoHeader(TipoHeader tipoHeader) {
        this.tipoHeader = tipoHeader;
        return this;
    }

    public void setTipoHeader(TipoHeader tipoHeader) {
        this.tipoHeader = tipoHeader;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public Header serviceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        return this;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Header header = (Header) o;
        if (header.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, header.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Header{" +
            "id=" + id +
            ", serviceGroupId='" + serviceGroupId + "'" +
            ", token='" + token + "'" +
            ", activo='" + activo + "'" +
            '}';
    }
}
