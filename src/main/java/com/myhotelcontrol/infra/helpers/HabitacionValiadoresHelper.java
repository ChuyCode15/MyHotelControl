package com.myhotelcontrol.infra.helpers;

import com.myhotelcontrol.infra.helpers.exceptions.DuplicateResourceException;
import com.myhotelcontrol.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitacionValiadoresHelper {

    private final HabitacionRepository habitacionRepository;

    public void validaNombreHabitacionNoExista(String nombre) {
        if (habitacionRepository.existsByNombreAndActivoTrue(nombre)) {
            throw new DuplicateResourceException("Habitacion ya existe en el sistema");
        }
    }

}
