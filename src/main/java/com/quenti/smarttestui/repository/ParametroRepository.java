package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Parametro;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Parametro entity.
 */
@SuppressWarnings("unused")
public interface ParametroRepository extends JpaRepository<Parametro,Long> {

    @Query("select distinct parametro from Parametro parametro left join fetch parametro.metodos")
    List<Parametro> findAllWithEagerRelationships();

    @Query("select parametro from Parametro parametro left join fetch parametro.metodos where parametro.id =:id")
    Parametro findOneWithEagerRelationships(@Param("id") Long id);

}
