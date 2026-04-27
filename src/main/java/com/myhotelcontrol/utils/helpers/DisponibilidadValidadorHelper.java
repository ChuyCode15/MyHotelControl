package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.infra.helpers.exceptions.NotFoundResorceException;
import com.myhotelcontrol.repository.DisponibilidadRepository;
import jakarta.validation.constraints.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DisponibilidadValidadorHelper {

    private final DisponibilidadRepository disponibilidadRepository;

    public void validaDisponibilidadDeCuarto(DatosDetalleRegistroReserva datos) {
        var fechaDeSalida = datos.fechaEntrada().plusDays(datos.cantidadNoches());
        if (!disponibilidadRepository.existsByHabitacionIdAndFechaBetweenAndActivoTrue(datos.habitacionId(), datos.fechaEntrada(), fechaDeSalida)) {
            throw new NotFoundResorceException("Habitacion no disponible..");
        }
    }

    public void validaNoExisteRegistroDia(LocalDate fecha) {

        if (fecha.isBefore(LocalDate.now())) {
            throw new NotFoundResorceException("La fecha no puede ser en el pasado");
        }

        if (disponibilidadRepository.existsByFechaAndActivoTrue(fecha)) {
            throw new NotFoundResorceException("Habitación ya está registrada para: " + fecha);
        }
    }

}
