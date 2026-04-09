package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.domain.habitaciones.dto.DatosDetalleHabitacion;
import com.myhotelcontrol.infra.helpers.exceptions.DuplicateResourceException;
import com.myhotelcontrol.repository.HuespedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HuespedValidacionesHelper {

    private final HuespedRepository huespedRepository;

    public void validaHuespedNoExiste(Integer idCard) {
        if (huespedRepository.existsByIdCardAndActivoTrue(idCard)) {
            throw new DuplicateResourceException("Huesped ya existe en el sistema, el Id card: " + idCard + " ya esta registrado!");
        }
    }
}
