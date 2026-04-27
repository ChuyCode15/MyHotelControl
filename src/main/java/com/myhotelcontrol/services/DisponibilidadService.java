package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.reservas.Disponibilidad;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.domain.reservas.mapper.ReservaMapper;
import com.myhotelcontrol.enums.EstadoReserva;
import com.myhotelcontrol.repository.DisponibilidadRepository;
import com.myhotelcontrol.utils.helpers.DisponibilidadValidadorHelper;
import com.myhotelcontrol.utils.helpers.HabitacionValidadorHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisponibilidadService {

    private final DisponibilidadValidadorHelper disponibilidadValidadorHelper;

    private final HabitacionValidadorHelper habitacionValidadorHelper;

    private final DisponibilidadRepository disponibilidadRepository;

    private final ReservaMapper reservaMapper;

    @Transactional
    public UUID generarRegistroDisponibilidad(DatosDetalleRegistroReserva datos, UUID reservaId) {

        disponibilidadValidadorHelper.validaDisponibilidadDeCuarto(datos);

        var numeroHabitacion = habitacionValidadorHelper
                .numeroBuscarHabitacionId(datos.habitacionId());
        var fechaSalida = datos.fechaEntrada()
                .plusDays(datos.cantidadNoches());

        var fechaActual = datos.fechaEntrada();
        var nocheNumero = 1;

        while (fechaActual.isBefore(fechaSalida)) {

            disponibilidadValidadorHelper.validaNoExisteRegistroDia(fechaActual);

            var disponibilidad = construirDisponibilidad(
                    datos,
                    reservaId,
                    numeroHabitacion,
                    fechaActual,
                    fechaSalida,
                    nocheNumero
            );

            disponibilidadRepository.save(disponibilidad);

            fechaActual = fechaActual.plusDays(1);
            nocheNumero++;

        }


        return reservaId;
    }

    private Disponibilidad construirDisponibilidad(
            DatosDetalleRegistroReserva datos,
            UUID reservaId,
            String numeroHabitacion,
            LocalDate fechaActual,
            LocalDate fechaSalida,
            Integer nocheNumero
    ) {
        var disponibilidad = new Disponibilidad();
        disponibilidad.setFecha(fechaActual);                        // 📅 El día
        disponibilidad.setHabitacionId(datos.habitacionId());        // 🏠 Qué habitación
        disponibilidad.setHabitacionNumero(numeroHabitacion);        // ⚡ Rendimiento
        disponibilidad.setReservaId(reservaId);                      // 🔗 De quién
        disponibilidad.setFechaSalidaReserva(fechaSalida);           // ⚡ Rendimiento
        disponibilidad.setNocheNumero(nocheNumero);                  // 📊 "Noche X..."
        disponibilidad.setTotalNoches(datos.cantidadNoches());       // 📊 "...de N"
        disponibilidad.setEstado(EstadoReserva.PENDIENTE);           // 📋 Estado
        disponibilidad.setActivo(true);                              // 🗑️ Soft delete

        return disponibilidad;
    }

}

