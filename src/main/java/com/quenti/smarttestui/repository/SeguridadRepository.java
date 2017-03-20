package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Seguridad;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Seguridad entity.
 */
@SuppressWarnings("unused")
public interface SeguridadRepository extends JpaRepository<Seguridad,Long> {

}
