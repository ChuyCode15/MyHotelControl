package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.reservas.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, UUID> {

    @Query("""
           SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END 
           FROM Disponibilidad d 
           WHERE d.habitacionId = :habitacionId 
           AND d.fecha >= :fechaInicio 
           AND d.fecha <= :fechaFin
           """)
    Boolean existsByHabitacionIdAndFechaBetweenAndActivoTrue(
            @Param("habitacionId") UUID habitacionId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    Boolean existsByFechaAndActivoTrue(LocalDate fecha);
}
