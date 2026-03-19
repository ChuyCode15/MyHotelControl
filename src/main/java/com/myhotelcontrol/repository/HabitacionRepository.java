package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNombreAndActivoTrue(String nombre);

    boolean existsByNombreAndActivoTrue(String nombre);
}
