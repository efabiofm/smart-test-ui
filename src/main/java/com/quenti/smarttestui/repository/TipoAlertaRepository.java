package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.TipoAlerta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoAlerta entity.
 */
@SuppressWarnings("unused")
public interface TipoAlertaRepository extends JpaRepository<TipoAlerta,Long> {

}
