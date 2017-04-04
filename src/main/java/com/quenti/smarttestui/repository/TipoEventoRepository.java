package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.TipoEvento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoEvento entity.
 */
@SuppressWarnings("unused")
public interface TipoEventoRepository extends JpaRepository<TipoEvento,Long> {

    List<TipoEvento> findByActivoTrue();

}
