package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Header;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Header entity.
 */
@SuppressWarnings("unused")
public interface HeaderRepository extends JpaRepository<Header,Long> {

}
