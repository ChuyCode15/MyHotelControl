package com.myhotelcontrol.controller;

import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleListarHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosRegistroTipoHabitacion;
import com.myhotelcontrol.services.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<PagedModel<DatosDetalleListarHabitacion>> listaHabitaciones(@PageableDefault(size = 10, sort = {"nombre"}) Pageable pageable, PagedResourcesAssembler assembler) {
        var listaHabitaciones = habitacionService.listarHabitaciones(pageable);
        return ResponseEntity.ok(assembler.toModel(listaHabitaciones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleHabitacion> habitacionId(@PathVariable UUID id) {
        var detalleHabitacionId = habitacionService.buscarHabitacionId(id);
        return ResponseEntity.ok(detalleHabitacionId);
    }

}
