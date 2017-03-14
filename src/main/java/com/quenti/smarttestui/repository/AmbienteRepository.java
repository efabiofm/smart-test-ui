package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Ambiente;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Ambiente entity.
 */
@SuppressWarnings("unused")
public interface AmbienteRepository extends JpaRepository<Ambiente,Long> {

    @Query("select distinct ambiente from Ambiente ambiente left join fetch ambiente.modulos")
    List<Ambiente> findAllWithEagerRelationships();

    @Query("select ambiente from Ambiente ambiente left join fetch ambiente.modulos where ambiente.id =:id")
    Ambiente findOneWithEagerRelationships(@Param("id") Long id);

}
