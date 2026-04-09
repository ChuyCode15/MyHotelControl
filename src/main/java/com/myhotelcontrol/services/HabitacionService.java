package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleListarHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosRegistroTipoHabitacion;
import com.myhotelcontrol.domain.habitaciones.mapper.HabitacioMapper;
import com.myhotelcontrol.repository.HabitacionRepository;
import com.myhotelcontrol.utils.helpers.HabitacionValidadorHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionValidadorHelper habitacionValiadoresHelper;

    private final HabitacioMapper habitacioMapper;

    private final HabitacionRepository habitacionRepository;

    @Transactional
    public DatosDetalleHabitacion registrarTipoHabitacion(DatosRegistroTipoHabitacion datos) {

        habitacionValiadoresHelper.validaNombreHabitacionNoExista(datos.nombre());
        habitacionValiadoresHelper.validaNumeroHabitacionNoExista(datos.numero());

        var habitacion = habitacioMapper.toEntiy(datos);

        habitacionRepository.save(habitacion);
        return habitacioMapper.toDetalleDTO(habitacion);
    }

    public Page<DatosDetalleListarHabitacion> listarHabitaciones(Pageable pageable) {
        Page<DatosDetalleListarHabitacion> listarHabitacions = habitacionRepository.findAllByActivoTrue(pageable).map(habitacioMapper::toDetalleListarDTO);
        return listarHabitacions;
    }

    public DatosDetalleHabitacion buscarHabitacionId(UUID id) {
        var habitacion = habitacionRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Habitacion no encontrada con el id: "+ id));
        return  habitacioMapper.toDetalleDTO(habitacion);
    }
}

