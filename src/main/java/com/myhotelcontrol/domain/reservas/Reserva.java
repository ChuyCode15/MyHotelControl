package com.myhotelcontrol.domain.reservas;

import com.myhotelcontrol.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservas")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // 👤 HUESPED - Denormalizado ⚡
    @Column(name = "huesped_id", nullable = false)
    private UUID huespedId;
    @Column(name = "nombre_huesped", nullable = false)
    private String nombreHuesped;

    // 🏠 HABITACION - Denormalizado ⚡
    @Column(name = "habitacion_id", nullable = false)
    private UUID habitacionId;
    @Column(name = "numero_habitacion", nullable = false)
    private String numeroHabitacion;

    // 📅 FECHAS
    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;
    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;
    @Column(name = "cantidad_noches", nullable = false)
    private Integer cantidadNoches;

    // ✅ RELACION CON DISPONIBILIDAD
    @OneToMany
    @JoinColumn(name = "reserva_id")
    private List<Disponibilidad> disponibilidades;

    // 💰 MONTOS
    @Column(name = "precio_noche", nullable = false)
    private BigDecimal precioPorNoche;
    @Column(name = "monto_anticipo")
    private BigDecimal montoAnticipo;
    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    // 📊 ESTADO
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    // ⏰ CONTROL DE TIEMPO
    @Column(name = "fecha_limite_confirmacion")
    private LocalDateTime fechaLimiteConfirmacion; // ✅ Para auto-cancelación

    // 📋 AUDITORIA
    @Column(name = "fecha_creacion", updatable = false) // ✅ Solo escritura
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // 🗑️ SOFT DELETE
    private Boolean activo = true; // ✅ Consistente con Disponibilidad

}

