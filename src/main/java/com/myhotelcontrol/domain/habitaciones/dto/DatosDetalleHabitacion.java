package com.myhotelcontrol.domain.habitaciones.dto;

import com.myhotelcontrol.domain.habitaciones.DetalleCamas;

import java.math.BigDecimal;
import java.util.List;

public record DatosDetalleHabitacion(
        Long id,
        String nombre,
        Integer numero,
        List<DetalleCamas>  detalleCamas,
        BigDecimal precio
) {
}
