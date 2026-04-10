package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.infra.helpers.exceptions.DuplicateResourceException;
import com.myhotelcontrol.repository.HabitacionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HabitacionValidadorHelper {

    private final HabitacionRepository habitacionRepository;

    public void validaNombreHabitacionNoExista(@NotNull String nombre) {
        if (habitacionRepository.existsByNombreAndActivoTrue(nombre)) {
            throw new DuplicateResourceException("El nombre de habitacion " + nombre + " ya existe en el sistema");
        }
    }

    public void validaNumeroHabitacionNoExista(@NotNull Integer numero) {
        if (habitacionRepository.existsByNumeroAndActivoTrue(numero)) {
            throw new DuplicateResourceException("El numero de  habitacion " + numero + " ya esta registrado en el sistema");
        }
    }

    public Habitacion buscarHabitacionId(UUID id) {
        return habitacionRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Habitacion no encontrada con el id: "+ id));
    }

}
