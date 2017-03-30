package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.ServiceProvider;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceProvider entity.
 */
@SuppressWarnings("unused")
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Long> {

    List<ServiceProvider> findByActivoTrue();

}
