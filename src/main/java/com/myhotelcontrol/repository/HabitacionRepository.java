package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HabitacionRepository extends JpaRepository<Habitacion, UUID> {


    Boolean existsByNombreAndActivoTrue(@NotNull String nombre);

    Boolean existsByNumeroAndActivoTrue(@NotNull Integer numero);

    Page<Habitacion> findAllByActivoTrue(Pageable pageable);


    Optional<Habitacion> findByIdAndActivoTrue(UUID id);
}
