package com.myhotelcontrol.domain.habitaciones.dto;

import java.math.BigDecimal;
import java.util.List;

public record DatosRegistroTipoHabitacion(
        String nombre,
        Integer numero,
        List<DatosDetalleCama> configuracionCamas,
        BigDecimal precio,
        BigDecimal precio2,
        BigDecimal precio3,
        BigDecimal precio4
) {
    public record DatosDetalleCama(
            String tamano,
            Integer cantidad
    ) {
    }
}
