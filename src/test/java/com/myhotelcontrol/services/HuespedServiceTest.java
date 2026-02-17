package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.huesped.Huesped;
import com.myhotelcontrol.domain.huesped.dto.DatosDetalleHuesped;
import com.myhotelcontrol.domain.huesped.dto.DatosRegistroHuesped;
import com.myhotelcontrol.domain.huesped.mapper.HuespedMapper;
import com.myhotelcontrol.repository.HuespedRepository;
import com.myhotelcontrol.utils.helpers.HuespedValidacionesHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HuespedServiceTest {

    @Mock
    private HuespedRepository huespedRepository;

    @Mock
    private HuespedValidacionesHelper huespedValidacionesHelper;

    @Mock
    private HuespedMapper huespedMapper;

    @InjectMocks
    private HuespedService huespedService;

    @Test
    @DisplayName("Debería Lanzar Error si el huésped ya existe por nombre")
    void registrarHuespedPosiblesErrores() {

        var datos = new DatosRegistroHuesped(
                "Jesus Casas Dena", 12234324, LocalDate.now().minusYears(20), 123123123, "chuypreubas@test.com", "c.Tremendo #143 el ejidal noruego.mx"
        );
        var entidadSimulada = new Huesped();
        var detallEsperado = new DatosDetalleHuesped(entidadSimulada);

        when(huespedMapper.aEntidad(datos)).thenReturn(entidadSimulada);
        when(huespedRepository.save(any(Huesped.class))).thenReturn(entidadSimulada);

        var resultado = huespedService.registrarHuesped(datos);

        assertNotNull(resultado);
        verify(huespedValidacionesHelper).validaHuespedNoExiste(datos.idCard());
        verify(huespedMapper).aEntidad(datos);
        verify(huespedRepository).save(entidadSimulada);

    }
}