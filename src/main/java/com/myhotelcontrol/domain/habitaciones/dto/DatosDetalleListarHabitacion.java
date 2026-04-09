package com.myhotelcontrol.domain.habitaciones.dto;

import com.myhotelcontrol.domain.habitaciones.DetalleCamas;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DatosDetalleListarHabitacion(
        UUID id,
        String nombre,
        Integer numero,
        List<DetalleCamas> detalleCamas,
        BigDecimal precio
) {
}
