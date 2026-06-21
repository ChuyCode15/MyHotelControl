package com.myhotelcontrol.controller;

import com.myhotelcontrol.domain.reservas.dto.DatosRegistroReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosBusquedaReserva;
import com.myhotelcontrol.domain.reservas.dto.HabitacionDisponibleResponse;
import com.myhotelcontrol.services.ReservaServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/reservaciones")
@RequiredArgsConstructor
public class ResevaController {

    public final ReservaServices reservaServices;

    @PostMapping
    public ResponseEntity<DatosDetalleReserva> registrarReserva(@RequestBody @Valid DatosRegistroReserva datos, UriComponentsBuilder uCB) {
        var reserva = reservaServices.registrarReservaNueva(datos);
        var uri = uCB.path("/reservaciones/{id}").buildAndExpand(reserva.id()).toUri();
        return ResponseEntity.created(uri).body(reserva);
    }

    @PostMapping("/buscar-disponibilidad")
    public ResponseEntity<List<HabitacionDisponibleResponse>> buscarDisponibilidad(
            @RequestBody @Valid DatosBusquedaReserva busqueda) {
        var habitaciones = reservaServices.buscarHabitacionesDisponibles(busqueda);
        return ResponseEntity.ok(habitaciones);
    }

}
