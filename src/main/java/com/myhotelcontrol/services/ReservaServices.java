package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.reservas.Disponibilidad;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleReserva;
import com.myhotelcontrol.domain.reservas.mapper.ReservaMapper;
import com.myhotelcontrol.repository.ReservaRepository;
import com.myhotelcontrol.utils.helpers.HabitacionValidadorHelper;
import com.myhotelcontrol.utils.helpers.HuespedValidacionesHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservaServices {

    private final HuespedValidacionesHelper huespedValidacionesHelper;

    private final HabitacionValidadorHelper habitacionValidadorHelper;

    private final DisponibilidadService disponibilidadService;

    private final ReservaRepository reservaRepository;

    private final ReservaMapper reservaMapper;

    public DatosDetalleReserva registrarReservaNueva(DatosDetalleRegistroReserva datos) {

        huespedValidacionesHelper.validaHuespedExisteId(datos.huespedId());
        habitacionValidadorHelper.validaHabitacionExisteId(datos.habitacionId());

        var disponibilidadId = disponibilidadService.generarRegistroDisponibilidad(datos);



        LocalDate fechaEntrada

        var reserva = reservaMapper.toEntity(datos);
        reservaRepository.save(reserva);

        return reservaMapper.toDtoDetalle(reserva);
    }

}
