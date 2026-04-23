package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.utils.helpers.DisponibilidadValidadorHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisponibilidadService {

    private final DisponibilidadValidadorHelper disponibilidadValidadorHelper;

    public UUID generarRegistroDisponibilidad(DatosDetalleRegistroReserva datos) {
        disponibilidadValidadorHelper.validaDisponibilidadDeCuarto(datos);
        



        return UUID.randomUUID();


    }
}
