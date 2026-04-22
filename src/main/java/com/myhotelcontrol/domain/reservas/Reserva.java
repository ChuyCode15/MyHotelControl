package com.myhotelcontrol.domain.reservas;

import com.myhotelcontrol.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // Relación con el huésped (puede ser un ID o una entidad)
    @Column(name = "id_huesped")
    private UUID huespedId;
    @Column(name = "nombre_huesped")
    private String nombreHuesped;

    @Column(name = "numero_habitacion")
    private String numeroHabitacion;

    @Column(name = "habitacion_id")
    private UUID habitacionId;

    @Column(name = "numero_noches")
    private Integer numeroNoches;

    @Column(name = "fecha_entrada")
    private LocalDate fechaEntrada;
    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    // Gestión de dinero
    @Column(name = "precio_noche")
    private BigDecimal precioPorNoche;
    @Column(name = "monto_anticipo")
    private BigDecimal montoAnticipo;
    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

}

