package com.myhotelcontrol.domain.habitaciones.mapper;

import com.myhotelcontrol.domain.habitaciones.DetalleCamas;
import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleListarHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosRegistroTipoHabitacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HabitacioMapper {

    Habitacion toEntiy(DatosRegistroTipoHabitacion datos);

    @Mapping(target = "detalleCamas", source = "configuracionCamas")
    DatosDetalleHabitacion toDetalleDTO(Habitacion habitacion);

    @Mapping(target = "tamanoCama", source = "tamano")
    DetalleCamas toEmbeddable(DatosRegistroTipoHabitacion.DatosDetalleCama dto);

    @Mapping(target = "detalleCamas", source = "configuracionCamas")
    DatosDetalleListarHabitacion toDetalleListarDTO(Habitacion habitacion);



}
