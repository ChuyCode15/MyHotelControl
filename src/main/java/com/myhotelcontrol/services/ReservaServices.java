package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleReserva;
import com.myhotelcontrol.domain.reservas.mapper.ReservaMapper;
import com.myhotelcontrol.enums.EstadoReserva;
import com.myhotelcontrol.repository.ReservaRepository;
import com.myhotelcontrol.utils.helpers.HabitacionValidadorHelper;
import com.myhotelcontrol.utils.helpers.HuespedValidacionesHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ReservaServices {

    private final HuespedValidacionesHelper huespedValidacionesHelper;

    private final HabitacionValidadorHelper habitacionValidadorHelper;

    private final DisponibilidadService disponibilidadService;

    private final ReservaRepository reservaRepository;

    private final ReservaMapper reservaMapper;

    public DatosDetalleReserva registrarReservaNueva(DatosDetalleRegistroReserva datos) {

        // 1. VALIDACIONES
        huespedValidacionesHelper.validaHuespedExisteId(datos.huespedId());
        habitacionValidadorHelper.validaHabitacionExisteId(datos.habitacionId());
        var precioPorNoche = habitacionValidadorHelper.buscarPrecioHabitacionId(datos.habitacionId());

        // 2. CÁLCULOS
        var fechaSalida = datos.fechaEntrada()
                .plusDays(datos.cantidadNoches());

        var montoTotal = precioPorNoche
                .multiply(BigDecimal.valueOf(datos.cantidadNoches()));

        var montoAnticipo = montoTotal
                .multiply(BigDecimal.valueOf(0.30)); // 30% depósito

        var fechaLimite = LocalDateTime.now()
                .plusHours(24); // 24h para confirmar

        // 3. CONSTRUIR RESERVA
        var reserva = reservaMapper.toEntity(datos);
        reserva.setFechaSalida(fechaSalida);
        reserva.setMontoTotal(montoTotal);
        reserva.setMontoAnticipo(montoAnticipo);
        reserva.setFechaLimiteConfirmacion(fechaLimite);
        reserva.setEstado(EstadoReserva.PENDIENTE);

        // 4. GUARDAR RESERVA PRIMERO ✅ Ya tiene UUID
        reservaRepository.save(reserva);

        // 5. GENERAR DISPONIBILIDAD con el ID real
        disponibilidadService
                .generarRegistroDisponibilidad(datos, reserva.getId());

        // 6. RETORNAR DTO
        return reservaMapper.toDtoDetalle(reserva);
    }

}
