package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.infra.helpers.exceptions.NotFoundResorceException;
import com.myhotelcontrol.repository.DisponibilidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisponibilidadValidadorHelper {

    private final DisponibilidadRepository disponibilidadRepository;

    public void validaDisponibilidadDeCuarto(DatosDetalleRegistroReserva datos) {
        var fechaDeSalida = datos.fechaEntrada().plusDays(datos.cantidadNoches());
        if (!disponibilidadRepository.existsByHabitacionIdAndFechaBetweenAndActivoTrue(datos.habitacionId(),datos.fechaEntrada(),fechaDeSalida)){
            throw new NotFoundResorceException("Habitacion no disponible..");
        }
    }

}
