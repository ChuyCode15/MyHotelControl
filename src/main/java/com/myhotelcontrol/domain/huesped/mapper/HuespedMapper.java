package com.myhotelcontrol.domain.huesped.mapper;


import com.myhotelcontrol.domain.huesped.Huesped;
import com.myhotelcontrol.domain.huesped.dto.DatosDetalleHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosDetalleListarHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosRegistroHuesped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HuespedMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", constant = "true")
    Huesped aEntidad(DatosRegistroHuesped datos);

    DatosDetalleHuesped aDto(Huesped huesped);


    DatosDetalleListarHuesped toDetalleListarDTO(Huesped huesped);
}

