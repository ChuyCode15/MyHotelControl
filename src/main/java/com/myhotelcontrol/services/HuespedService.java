package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.huesped.Huesped;
import com.myhotelcontrol.domain.huesped.dto.DatosDetalleHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosRegistroHuesped;
import com.myhotelcontrol.domain.huesped.mapper.HuespedMapper;
import com.myhotelcontrol.repository.HuespedRepository;
import com.myhotelcontrol.utils.helpers.HuespedValidacionesHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HuespedService {

    private final HuespedValidacionesHelper huespedValidacionesHelper;

    private final HuespedMapper huespedMapper;

    private final HuespedRepository huespedRepository;

    public DatosDetalleHuesped registrarHuesped(DatosRegistroHuesped datos) {
        huespedValidacionesHelper.validaHuespedNoExiste(datos.idCard());
        var huespedNuevo = huespedMapper.aEntidad(datos);
        huespedRepository.save(huespedNuevo);
        return new DatosDetalleHuesped(huespedNuevo);
    }

    private Huesped crearHuesped(DatosRegistroHuesped datos) {
        var nuevoHuesped = huespedMapper.aEntidad(datos);
        return huespedRepository.save(nuevoHuesped);
    }
}
