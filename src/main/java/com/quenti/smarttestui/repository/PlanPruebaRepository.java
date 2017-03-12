package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.PlanPrueba;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PlanPrueba entity.
 */
@SuppressWarnings("unused")
public interface PlanPruebaRepository extends JpaRepository<PlanPrueba,Long> {

    @Query("select distinct planPrueba from PlanPrueba planPrueba left join fetch planPrueba.pruebas")
    List<PlanPrueba> findAllWithEagerRelationships();

    @Query("select planPrueba from PlanPrueba planPrueba left join fetch planPrueba.pruebas where planPrueba.id =:id")
    PlanPrueba findOneWithEagerRelationships(@Param("id") Long id);

}
