package com.myhotelcontrol.domain.reservas.mapper;

import com.myhotelcontrol.domain.reservas.Disponibilidad;
import com.myhotelcontrol.domain.reservas.Reserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleRegistroReserva;
import com.myhotelcontrol.domain.reservas.dto.DatosDetalleReserva;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    Reserva toEntity(DatosDetalleRegistroReserva reserva);

    DatosDetalleReserva toDtoDetalle(Reserva reserva);

    Disponibilidad toDisponibilidad(DatosDetalleRegistroReserva Disponibilidad, String numeroHabitacion, LocalDate fechaSalida, LocalDate fechaVeridicada, String estado);

}
