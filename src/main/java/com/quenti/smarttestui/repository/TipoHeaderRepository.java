package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.TipoHeader;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoHeader entity.
 */
@SuppressWarnings("unused")
public interface TipoHeaderRepository extends JpaRepository<TipoHeader,Long> {

}
