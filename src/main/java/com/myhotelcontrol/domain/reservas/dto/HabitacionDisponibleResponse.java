package com.myhotelcontrol.domain.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Habitación disponible para venta")
public record HabitacionDisponibleResponse(

        @Schema(description = "ID de la habitación")
        UUID id,

        @Schema(description = "Nombre de la habitación", example = "Suite Deluxe")
        String nombre,

        @Schema(description = "Número de habitación", example = "101")
        String numero,

        @Schema(description = "Configuración de camas")
        List<String> camas,

        @Schema(description = "Capacidad máxima de huéspedes", example = "4")
        Integer capacidadMaxima,

        @Schema(description = "Precio por noche para la cantidad de huéspedes", example = "150.00")
        BigDecimal precioPorNoche,

        @Schema(description = "Precio total para la estancia", example = "450.00")
        BigDecimal precioTotal,

        @Schema(description = "Anticipo requerido (30%)", example = "135.00")
        BigDecimal anticipoRequerido
) {
}
