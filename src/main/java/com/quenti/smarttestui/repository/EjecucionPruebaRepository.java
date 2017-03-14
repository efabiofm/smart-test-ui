package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.EjecucionPrueba;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EjecucionPrueba entity.
 */
@SuppressWarnings("unused")
public interface EjecucionPruebaRepository extends JpaRepository<EjecucionPrueba,Long> {

}
