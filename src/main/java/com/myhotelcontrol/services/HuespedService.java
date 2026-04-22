package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.huesped.dto.DatosDetalleHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosDetalleListarHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosRegistroHuesped;
import com.myhotelcontrol.domain.huesped.mapper.HuespedMapper;
import com.myhotelcontrol.repository.HuespedRepository;
import com.myhotelcontrol.utils.helpers.HuespedValidacionesHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HuespedService {

    private final HuespedValidacionesHelper huespedValidacionesHelper;

    private final HuespedMapper huespedMapper;

    private final HuespedRepository huespedRepository;

    public DatosDetalleHuesped registrarHuesped(DatosRegistroHuesped datos) {

        huespedValidacionesHelper.validaHuespedNoExiste(datos.idCard());
        huespedValidacionesHelper.validaNumeroNoExiste(datos.telefono());
        var huespedNuevo = huespedMapper.aEntidad(datos);
        huespedRepository.save(huespedNuevo);
        return huespedMapper.aDto(huespedNuevo);

    }

    public Page<DatosDetalleListarHuesped> listarHuespedes(Pageable pageable) {
        Page<DatosDetalleListarHuesped> listaDeHuespedes = huespedRepository.findAllByActivoTrue(pageable)
                .map(huespedMapper::toDetalleListarDTO);
        return listaDeHuespedes;
    }

}
