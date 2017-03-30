package com.quenti.smarttestui.repository;

import com.quenti.smarttestui.domain.Alerta;

import org.springframework.data.jpa.repository.*;

import javax.swing.text.StyledEditorKit;
import java.util.List;

/**
 * Spring Data JPA repository for the Alerta entity.
 */
@SuppressWarnings("unused")
public interface AlertaRepository extends JpaRepository<Alerta,Long> {

    List<Alerta> findByActivoTrue();

}
