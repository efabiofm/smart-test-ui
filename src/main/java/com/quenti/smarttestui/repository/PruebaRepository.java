package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Prueba;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Prueba entity.
 */
@SuppressWarnings("unused")
public interface PruebaRepository extends JpaRepository<Prueba,Long> {

}
