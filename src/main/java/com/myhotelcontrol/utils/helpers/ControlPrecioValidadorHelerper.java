package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.repository.HabitacionRepository;
import com.myhotelcontrol.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ControlPrecioValidadorHelerper {

    private final HabitacionRepository habitacionRepository;

    private final  ReservaRepository reservaRepository;

    public BigDecimal precioReservaEstandar(LocalDate fecha, Habitacion habitacion) {
        if (fecha.isBefore(LocalDate.now())) {
            return habitacion.getPrecio();
        }
        return habitacion.getPrecio2();
    }

}
