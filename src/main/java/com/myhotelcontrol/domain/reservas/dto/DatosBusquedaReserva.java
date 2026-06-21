package com.myhotelcontrol.domain.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Datos para buscar habitaciones disponibles")
public record DatosBusquedaReserva(

        @Schema(description = "Fecha de entrada", example = "2024-12-01")
        @NotNull(message = "La fecha de entrada es obligatoria")
        @Future(message = "La fecha de entrada debe ser futura")
        LocalDate fechaEntrada,

        @Schema(description = "Cantidad de noches", example = "3")
        @NotNull(message = "La cantidad de noches es obligatoria")
        @Min(value = 1, message = "Debe buscar al menos 1 noche")
        Integer cantidadNoches,

        @Schema(description = "Cantidad de huéspedes", example = "2")
        @NotNull(message = "La cantidad de huéspedes es obligatoria")
        @Min(value = 1, message = "Debe haber al menos 1 huésped")
        Integer cantidadHuespedes
) {
}
