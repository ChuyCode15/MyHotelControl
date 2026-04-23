package com.myhotelcontrol.domain.reservas.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.UUID;

public record DatosDetalleRegistroReserva(

        @NotNull(message = "El id del huésped es obligatorio")
        UUID huespedId,

        @NotNull(message = "El id de la habitación es obligatorio")
        UUID habitacionId,

        @NotNull(message = "La fecha de entrada es obligatoria")
        @Future(message = "La fecha de entrada debe ser futura")
        LocalDate fechaEntrada,

        @NotNull(message = "La fecha de entrada es obligatoria")
        Integer cantidadNoches,

        // Opcional, puede ser 0
        BigDecimal montoAnticipo

) {
}
