package com.myhotelcontrol.domain.reservas.dto;

import com.myhotelcontrol.domain.reservas.Reserva;
import com.myhotelcontrol.enums.EstadoReserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record DatosDetalleReserva(

        UUID id,

        // Huésped
        UUID huespedId,
        String nombreHuesped,

        // Habitación
        UUID habitacionId,
        String numeroHabitacion,

        // Fechas
        LocalDate fechaEntrada,
        LocalDate fechaSalida,
        Integer numeroNoches,

        // Dinero
        BigDecimal precioPorNoche,
        BigDecimal montoAnticipo,
        BigDecimal montoTotal,

        // Estado
        EstadoReserva estado,
        LocalDateTime fechaCreacion

) {
}
