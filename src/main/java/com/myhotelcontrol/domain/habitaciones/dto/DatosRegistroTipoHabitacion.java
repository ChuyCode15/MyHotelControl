package com.myhotelcontrol.domain.habitaciones.dto;

import com.myhotelcontrol.enums.TamanoCama;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record DatosRegistroTipoHabitacion(

        @NotNull
        String nombre,

        @NotNull
        String numero,

        @NotNull
        List<DatosDetalleCama> configuracionCamas,

        @NotNull
        BigDecimal precio,
        BigDecimal precio2,
        BigDecimal precio3,
        BigDecimal precio4
) {
    public record DatosDetalleCama(
            @NotNull
            TamanoCama tamano,
            @NotNull
            Integer cantidad
    ) {
    }
}
