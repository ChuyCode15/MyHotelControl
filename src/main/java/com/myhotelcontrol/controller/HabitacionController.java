package com.myhotelcontrol.controller;

import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosRegistroTipoHabitacion;
import com.myhotelcontrol.services.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @PostMapping
    public ResponseEntity<DatosDetalleHabitacion> habitacionNueva(@RequestBody DatosRegistroTipoHabitacion datos, UriComponentsBuilder uComponentsBuilder) {

        var habitacionNueva = habitacionService.registrarTipoHabitacion(datos);

        var uri = uComponentsBuilder.path("/habitaciones/{id}").buildAndExpand(habitacionNueva.id()).toUri();

        return ResponseEntity.created(uri).body(habitacionNueva);
    }

}
