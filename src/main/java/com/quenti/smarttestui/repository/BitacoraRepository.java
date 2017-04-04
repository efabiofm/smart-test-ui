package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Bitacora;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bitacora entity.
 */
@SuppressWarnings("unused")
public interface BitacoraRepository extends JpaRepository<Bitacora,Long> {

}
