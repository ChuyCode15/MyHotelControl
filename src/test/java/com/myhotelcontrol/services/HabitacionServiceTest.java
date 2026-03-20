package com.myhotelcontrol.services;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosRegistroTipoHabitacion;
import com.myhotelcontrol.domain.habitaciones.mapper.HabitacioMapper;
import com.myhotelcontrol.infra.helpers.HabitacionValiadoresHelper;
import com.myhotelcontrol.repository.HabitacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {

    @InjectMocks
    private HabitacionService habitacionService;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private HabitacioMapper habitacioMapper;

    @Mock
    private HabitacionValiadoresHelper habitacionValiadoresHelper;

    @Test
    @DisplayName("Debería registrar una habitación con lista de camas exitosamente")
    void registrarTipoHabitacionExitosamente() {
        // registrar unas camas
        var listaCamas = List.of(
                new DatosRegistroTipoHabitacion.DatosDetalleCama("KING_SIZE", 1),
                new DatosRegistroTipoHabitacion.DatosDetalleCama("INDIVIDUAL", 2)
        );

        var datosRegistro = new DatosRegistroTipoHabitacion(
                "Suite Presidencial", 101, listaCamas, new BigDecimal("1500.00"),
                null, null, null
        );

        // simulamos MapStruct y Repositorio
        Habitacion habitacionOne = new Habitacion();
        when(habitacioMapper.toEntiy(datosRegistro)).thenReturn(habitacionOne);
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacionOne);
        when(habitacioMapper.toDetalleDTO(any())).thenReturn(new DatosDetalleHabitacion(100L, "Suite Presidencial" , 101,null , new BigDecimal("1500.00"), null, null,null));

        // Ejecutar el metodo del service
        var resultado = habitacionService.registrarTipoHabitacion(datosRegistro);

        assertNotNull(resultado);
        assertEquals("Suite Presidencial", resultado.nombre());

        // verificar el llamado al validador
        verify(habitacionValiadoresHelper, times(1)).validaNombreHabitacionNoExista(anyString());
        verify(habitacionRepository, times(1)).save(any(Habitacion.class));


    }
}