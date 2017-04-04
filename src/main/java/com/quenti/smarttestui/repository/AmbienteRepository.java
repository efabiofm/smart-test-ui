package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Ambiente;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ambiente entity.
 */
@SuppressWarnings("unused")
public interface AmbienteRepository extends JpaRepository<Ambiente,Long> {

    List<Ambiente> findByActivoTrue();

}
