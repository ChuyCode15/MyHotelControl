package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.reservas.dto.DatosRegistroReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosBusquedaReserva;
import com.myhotelcontrol.domain.reservas.dto.HabitacionDisponibleResponse;
import com.myhotelcontrol.domain.reservas.mapper.ReservaMapper;
import com.myhotelcontrol.enums.EstadoReserva;
import com.myhotelcontrol.repository.HabitacionRepository;
import com.myhotelcontrol.repository.DisponibilidadRepository;
import com.myhotelcontrol.repository.ReservaRepository;
import com.myhotelcontrol.utils.helpers.ControlPrecioValidadorHelerper;
import com.myhotelcontrol.utils.helpers.HabitacionValidadorHelper;
import com.myhotelcontrol.utils.helpers.HabitacionCapacidadHelper;
import com.myhotelcontrol.utils.helpers.HuespedValidacionesHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservaServices {

    private final HuespedValidacionesHelper huespedValidacionesHelper;

    private final HabitacionValidadorHelper habitacionValidadorHelper;

    private final ControlPrecioValidadorHelerper controlPrecioValidadorHelerper;

    private final DisponibilidadService disponibilidadService;

    private final ReservaRepository reservaRepository;

    private final ReservaMapper reservaMapper;

    private final HabitacionRepository habitacionRepository;

    private final DisponibilidadRepository disponibilidadRepository;

    @Transactional
    public DatosDetalleReserva registrarReservaNueva(DatosRegistroReserva datos) {

        // VALIDACIONES
        var huesped = huespedValidacionesHelper.buscarHuespedId(datos.huespedId());
        System.out.println("Ya se valido el huesped y este es su nombre: " + huesped.getNombre());
        var habitacion = habitacionValidadorHelper.buscarHabitacionId(datos.habitacionId());
        System.out.println("Ya se valido la habitacion: " + habitacion.getNombre());
        var precioPorNoche = habitacionValidadorHelper.buscarPrecioHabitacionId(datos.habitacionId());
        System.out.println("ya se cosiguio el precio : " + precioPorNoche);

        // CÁLCULOS
        var fechaSalida = datos.fechaEntrada().plusDays(datos.cantidadNoches());
        System.out.println("avanzando 07 fecha salida  : " + fechaSalida);
        var montoTotal = precioPorNoche.multiply(BigDecimal.valueOf(datos.cantidadNoches()));
        System.out.println("avanzando monto monto total 01  : " + montoTotal);
        var montoAnticipo = montoTotal.multiply(BigDecimal.valueOf(0.30));
        System.out.println("avanzando monto anticipo" + montoAnticipo);// 30% depósito
        var fechaLimite = LocalDateTime.now().plusHours(24);
        System.out.println("Avanzando fecha limite :" + fechaLimite);// 24h para confirmar

        var precioHabitacion = controlPrecioValidadorHelerper.precioReservaEstandar(datos.fechaEntrada(), habitacion);
        System.out.println("avanzando precio habitación " + precioHabitacion);

        // CONSTRUIR RESERVA
        var reserva = reservaMapper.toEntity(datos);
        reserva.setNombreHuesped(huesped.getNombre());
        reserva.setNumeroHabitacion(habitacion.getNumero());

        reserva.setFechaSalida(fechaSalida);
        reserva.setMontoTotal(montoTotal);
        reserva.setMontoAnticipo(montoAnticipo);
        reserva.setFechaLimiteConfirmacion(fechaLimite);
        reserva.setEstado(EstadoReserva.SOLICITUD);
        reserva.setPrecioPorNoche(precioHabitacion);

        // GUARDAR RESERVA PRIMERO ✅ Ya tiene UUID
        reservaRepository.save(reserva);
        System.out.println("atorado en reserva: " + reserva.getId());

        // GENERAR DISPONIBILIDAD con el ID real
        disponibilidadService.generarRegistroDisponibilidad(datos, reserva.getId());

        // RETORNAR DTO
        return reservaMapper.toDtoDetalle(reserva);
    }

    public List<HabitacionDisponibleResponse> buscarHabitacionesDisponibles(DatosBusquedaReserva busqueda) {
        var fechaSalida = busqueda.fechaEntrada().plusDays(busqueda.cantidadNoches());
        var habitacionesOcupadas = disponibilidadRepository.findHabitacionesOcupadas(
                busqueda.fechaEntrada(), fechaSalida.minusDays(1));

        var todasLasHabitaciones = habitacionRepository.findAllByActivoTrue();

        var habitacionesDisponibles = todasLasHabitaciones.stream()
                .filter(h -> !habitacionesOcupadas.contains(h.getId()))
                .filter(h -> {
                    int capacidad = HabitacionCapacidadHelper.calcularCapacidadMaxima(h);
                    return capacidad >= busqueda.cantidadHuespedes();
                })
                .collect(Collectors.toList());

        var resultado = new ArrayList<HabitacionDisponibleResponse>();

        for (var habitacion : habitacionesDisponibles) {
            var precioPorNoche = habitacionValidadorHelper.obtenerPrecioPorHuespedes(
                    habitacion, busqueda.cantidadHuespedes());
            var precioTotal = precioPorNoche.multiply(BigDecimal.valueOf(busqueda.cantidadNoches()));
            var anticipo = precioTotal.multiply(BigDecimal.valueOf(0.30))
                    .setScale(2, RoundingMode.HALF_UP);

            var camas = habitacion.getConfiguracionCamas().stream()
                    .map(c -> c.getCantidad() + " " + c.getTamanoCama().name().toLowerCase())
                    .collect(Collectors.toList());

            resultado.add(new HabitacionDisponibleResponse(
                    habitacion.getId(),
                    habitacion.getNombre(),
                    habitacion.getNumero(),
                    camas,
                    HabitacionCapacidadHelper.calcularCapacidadMaxima(habitacion),
                    precioPorNoche,
                    precioTotal,
                    anticipo
            ));
        }

        return resultado;
    }

}
