package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.TipoParametro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoParametro entity.
 */
@SuppressWarnings("unused")
public interface TipoParametroRepository extends JpaRepository<TipoParametro,Long> {

    List<TipoParametro> findByActivoTrue();

}
