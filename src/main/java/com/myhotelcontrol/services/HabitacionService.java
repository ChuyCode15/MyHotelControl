package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosRegistroTipoHabitacion;
import com.myhotelcontrol.domain.habitaciones.mapper.HabitacioMapper;
import com.myhotelcontrol.infra.helpers.HabitacionValiadoresHelper;
import com.myhotelcontrol.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionValiadoresHelper habitacionValiadoresHelper;

    private final HabitacioMapper habitacioMapper;

    private final HabitacionRepository habitacionRepository;


    public DatosDetalleHabitacion registrarTipoHabitacion(DatosRegistroTipoHabitacion datos) {
        if (datos != null) {
            habitacionValiadoresHelper.validaNombreHabitacionNoExista(datos.nombre());
        }
        var habitacion = habitacioMapper.toEntiy(datos);
        habitacionRepository.save(habitacion);

        return new DatosDetalleHabitacion(Habitacion);

    }
}
