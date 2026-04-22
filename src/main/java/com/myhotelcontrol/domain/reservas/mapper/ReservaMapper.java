package com.myhotelcontrol.domain.reservas.mapper;

import com.myhotelcontrol.domain.reservas.Reserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleReserva;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    Reserva toEntity(DatosDetalleRegistroReserva reserva);

    DatosDetalleReserva toDtoDetalle(Reserva reserva);

}
