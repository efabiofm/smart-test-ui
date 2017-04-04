package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Servicio;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Servicio entity.
 */
@SuppressWarnings("unused")
public interface ServicioRepository extends JpaRepository<Servicio,Long> {

    @Query("select distinct servicio from Servicio servicio left join fetch servicio.modulos")
    List<Servicio> findAllWithEagerRelationships();

    @Query("select servicio from Servicio servicio left join fetch servicio.modulos where servicio.id =:id")
    Servicio findOneWithEagerRelationships(@Param("id") Long id);

    List<Servicio> findByActivoTrue();

}
