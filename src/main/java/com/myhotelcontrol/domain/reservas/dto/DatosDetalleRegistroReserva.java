package com.myhotelcontrol.domain.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import java.util.UUID;

@Schema(description = "Datos para registrar una nueva reserva")
public record DatosDetalleRegistroReserva(

        @Schema(description = "ID del huésped", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "El id del huésped es obligatorio")
        UUID huespedId,

        @Schema(description = "ID de la habitación", example = "550e8400-e29b-41d4-a716-446655440001")
        @NotNull(message = "El id de la habitación es obligatorio")
        UUID habitacionId,

        @Schema(description = "Fecha de entrada", example = "2024-12-01")
        @NotNull(message = "La fecha de entrada es obligatoria")
        @Future(message = "La fecha de entrada debe ser futura")
        LocalDate fechaEntrada,

        @Schema(description = "Cantidad de noches", example = "4")
        @NotNull(message = "La cantidad de noches es obligatoria")
        @Min(value = 1, message = "Debe reservar al menos 1 noche")
        Integer cantidadNoches

        // ✅ montoAnticipo lo calcula el service (30%)
        // ✅ precioPorNoche lo trae el service desde Habitacion
) {
}
