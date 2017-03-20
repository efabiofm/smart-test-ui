package com.quenti.smarttestui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Seguridad.
 */
@Entity
@Table(name = "seguridad")
public class Seguridad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "jh_user_id", nullable = false)
    private Integer jhUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public Seguridad token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Seguridad fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getJhUserId() {
        return jhUserId;
    }

    public Seguridad jhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
        return this;
    }

    public void setJhUserId(Integer jhUserId) {
        this.jhUserId = jhUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seguridad seguridad = (Seguridad) o;
        if (seguridad.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, seguridad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seguridad{" +
            "id=" + id +
            ", token='" + token + "'" +
            ", fecha='" + fecha + "'" +
            ", jhUserId='" + jhUserId + "'" +
            '}';
    }
}
