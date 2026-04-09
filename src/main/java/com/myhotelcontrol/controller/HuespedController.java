package com.myhotelcontrol.controller;

import com.myhotelcontrol.domain.huesped.dto.DatosDetalleHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosRegistroHuesped;
import com.myhotelcontrol.services.HuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/huesped")
@RequiredArgsConstructor
public class HuespedController {

    private final HuespedService huespedService;

    @PostMapping
    public ResponseEntity<DatosDetalleHuesped> registrarHuesped(@RequestBody DatosRegistroHuesped datos, UriComponentsBuilder uC) {
        var huespedNuevo = huespedService.registrarHuesped(datos);
        var uri = uC.path("/huesped/{id}").buildAndExpand(huespedNuevo.id()).toUri();
        return ResponseEntity.created(uri).body(huespedNuevo);
    }

}
