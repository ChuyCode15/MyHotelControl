package com.myhotelcontrol.domain.reservas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "disponibilidad")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate fecha; // El día específico del calendario

    @Column(name = "habitacion_id", nullable = false)
    private UUID habitacionId;

    private String habitacionNumero;

    @Column(name = "reserva_id")
    private UUID reservaId;

    // --- CAMPOS EXTRA PARA RENDIMIENTO Y UX ---

    @Column(name = "fecha_salida_reserva")
    private LocalDate fechaSalidaReserva; // Para saber cuándo se libera sin ir a la tabla Reserva

    @Column(name = "noche_numero")
    private Integer nocheNumero; // Ejemplo: 1, 2, 3...

    @Column(name = "total_noches")
    private Integer totalNoches; // Para armar el "X de N" en el Front

    // ------------------------------------------

    private String estado; // OCUPADO, MANTENIMIENTO, BLOQUEADO

    private Boolean activo = true;

}
