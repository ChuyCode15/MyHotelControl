package com.myhotelcontrol.infra.helpers;

import com.myhotelcontrol.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitacionValiadoresHelper {

    private final HabitacionRepository habitacionRepository;

    public void validaNombreHabitacionNoExista(String nombre) {
        habitacionRepository.findByNombreAndActivoTrue(nombre)
                .orElseThrow(
                        () -> new RuntimeException("Habitacion encontrada")
                );
    }

}
