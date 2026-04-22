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
    private LocalDate fecha;

    @Column(name = "habitacion_id", nullable = false)
    private UUID habitacionId;

    // Dato redundante para evitar JOIN en el tablero principal
    private String habitacionNumero;

/*    // Solo guardamos la referencia para no sobrecargar la memoria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id") */
    private UUID reservaId;

    // Estado visual rápido (ej.: OCUPADO, MANTENIMIENTO, PENDIENTE)
    private String estado;

    private Boolean activo;

}
