package com.myhotelcontrol.utils.helpers;

import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.infra.helpers.exceptions.DuplicateResourceException;
import com.myhotelcontrol.infra.helpers.exceptions.NotFoundResorceException;
import com.myhotelcontrol.repository.HabitacionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    public void validaNumeroHabitacionNoExista(@NotNull String numero) {
        if (habitacionRepository.existsByNumeroAndActivoTrue(numero)) {
            throw new DuplicateResourceException("El numero de  habitacion " + numero + " ya esta registrado en el sistema");
        }
    }

    public Habitacion buscarHabitacionId(UUID id) {
        Habitacion habitacionEncontrada = habitacionRepository.findByIdAndActivoTrue(id)
                .orElseThrow(
                        () -> new RuntimeException("Habitación no encontrada con el id: " + id)
                );
        return habitacionEncontrada;
    }

    public void validaHabitacionExisteId(UUID uuid) {
        if (!habitacionRepository.existsByIdAndActivoTrue(uuid)) {
            throw new NotFoundResorceException("No existe una habitación con el Id : '" + uuid + "' registrado!");
        }
    }

    public String numeroBuscarHabitacionId(UUID id) {
        Habitacion habitacionEncontrada = habitacionRepository.findByIdAndActivoTrue(id)
                .orElseThrow(
                        () -> new RuntimeException("Habitación no encontrada con el id: " + id)
                );
        return habitacionEncontrada.getNumero();
    }

    public BigDecimal buscarPrecioHabitacionId( UUID uuid) {
        var nummeroHabitacion = habitacionRepository.findByIdAndActivoTrue(uuid).orElseThrow(
                () -> new NotFoundResorceException("Habitación no encontrada con el id: " + uuid)
        );
        return nummeroHabitacion.getPrecio();
    }
}
