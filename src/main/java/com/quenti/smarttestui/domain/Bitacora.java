package com.quenti.smarttestui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Bitacora.
 */
@Entity
@Table(name = "bitacora")
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "jh_user_id", nullable = false)
    private Integer jhUserId;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    private TipoEvento tipoEvento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Bitacora fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getJhUserId() {
        return jhUserId;
    }

    public Bitacora jhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
        return this;
    }

    public void setJhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Bitacora descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public Bitacora tipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
        return this;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bitacora bitacora = (Bitacora) o;
        if (bitacora.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bitacora.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bitacora{" +
            "id=" + id +
            ", fecha='" + fecha + "'" +
            ", jhUserId='" + jhUserId + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
