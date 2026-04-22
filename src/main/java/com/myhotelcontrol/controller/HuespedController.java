package com.myhotelcontrol.controller;

import com.myhotelcontrol.domain.huesped.dto.DatosDetalleHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosDetalleListarHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosRegistroHuesped;
import com.myhotelcontrol.services.HuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


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

    @GetMapping
    public ResponseEntity<PagedModel<DatosDetalleListarHuesped>> listarHuespedes(@PageableDefault(size = 10) Pageable pageable, PagedResourcesAssembler assembler) {
        var listaHuespedes = huespedService.listarHuespedes(pageable);
        return ResponseEntity.ok(assembler.toModel(listaHuespedes));
    }

}
