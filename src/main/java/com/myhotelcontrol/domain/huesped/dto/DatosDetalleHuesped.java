package com.myhotelcontrol.domain.huesped.dto;

import com.myhotelcontrol.domain.huesped.Huesped;
import com.myhotelcontrol.services.HuespedService;

public record DatosDetalleHuesped(

        Long id,
        String nombre,
        Integer idCard,
        Integer telefono,
        String correoElectronico
) {
    public DatosDetalleHuesped(Huesped huesped){
        this(
                huesped.getId(),
                huesped.getNombre(),
                huesped.getIdCard(),
                huesped.getTelefono(),
                huesped.getCorreoElectronico()

        );

    }
}
