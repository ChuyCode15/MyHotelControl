package com.myhotelcontrol.domain.huesped.dto;

import com.myhotelcontrol.domain.huesped.Huesped;

import java.util.UUID;

public record DatosDetalleListarHuesped(
        UUID id,
        String nombre,
        Integer idCard,
        Integer telefono,
        String correoElectronico
) {
    public DatosDetalleListarHuesped(Huesped huesped){
        this(
                huesped.getId(),
                huesped.getNombre(),
                huesped.getIdCard(),
                huesped.getTelefono(),
                huesped.getCorreoElectronico()

        );

    }
}



