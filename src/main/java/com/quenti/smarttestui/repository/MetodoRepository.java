package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Metodo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Metodo entity.
 */
@SuppressWarnings("unused")
public interface MetodoRepository extends JpaRepository<Metodo,Long> {

    @Query("select distinct metodo from Metodo metodo left join fetch metodo.servicios")
    List<Metodo> findAllWithEagerRelationships();

    @Query("select metodo from Metodo metodo left join fetch metodo.servicios where metodo.id =:id")
    Metodo findOneWithEagerRelationships(@Param("id") Long id);

}
