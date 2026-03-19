package com.myhotelcontrol.domain.habitaciones.mapper;

import com.myhotelcontrol.domain.habitaciones.DetalleCamas;
import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosRegistroTipoHabitacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabitacioMapper {

    Habitacion toEntiy(DatosRegistroTipoHabitacion datos);

    Habitacion toDTO(Habitacion habitacion);

    DetalleCamas toEmbeddable(DatosRegistroTipoHabitacion.DatosDetalleCama dto);

}
