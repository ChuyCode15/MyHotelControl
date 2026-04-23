package com.myhotelcontrol.controller;

import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleReserva;
import com.myhotelcontrol.services.ReservaServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/reservaciones")
@RequiredArgsConstructor
public class ResevaController {

    public final ReservaServices reservaServices;

    @PostMapping
    public ResponseEntity<DatosDetalleReserva> registrarReserva(@RequestBody DatosDetalleRegistroReserva datos, UriComponentsBuilder uCB) {
        var reserva = reservaServices.registrarReservaNueva(datos);
        var uri = uCB.path("/reservaciones/{id}").buildAndExpand(reserva.id()).toUri();
        return ResponseEntity.created(uri).body(reserva);
    }
}
